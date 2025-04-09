import { check } from "k6";
import http from "k6/http";

const BASE_URL = "http://localhost:8080"; // Substitua com a URL do seu backend
export const options = {
    stages: [
        { duration: "10s", target: 1 }, // Aumenta para 10 usuários em 10 segundos
        { duration: "30s", target: 1 }, // Mantém 10 usuários por 30 segundos
        { duration: "10s", target: 0 }, // Reduz para 0 usuários em 10 segundos
        ],
    thresholds: {
      "http_req_duration{expected_response:true}": ["p(95)<1000"], // 95% das requisições < 4s
      "http_req_failed": ["rate<0.05"], // Falha < 2% das requisições
      "checks": ["rate>0.83"], // Pelo menos 95% dos checks devem passar
    },
  }

export default function () {

  // Cenário 1: Seleção de restaurante e data para ver refeições e previsão do tempo
  let restaurantId = 1; 
  let date = "2025-04-12"; 
  let city = "Aveiro"; 
  
  // 1. Obter refeições do restaurante
  let mealsResponse = http.get(`${BASE_URL}/restaurants/${restaurantId}/meals`);
  check(mealsResponse, {
    "Refeições carregadas com sucesso": (r) => r.status === 200,
    "Verifica se o restaurante tem refeições": (r) => r.json().length > 0,
  });

  // 2. Obter previsão do tempo para o dia selecionado
  let weatherResponse = http.get(`${BASE_URL}/weather/${city}/${date}`);
  check(weatherResponse, {
    "Previsão do tempo carregada com sucesso": (r) => r.status === 200,
  });

  // Cenário 2: Fazer uma reserva
  let mealId = 1; // Exemplo: ID da refeição
  let reservationResponse = http.post(`${BASE_URL}/reservations/book/${mealId}`);
  check(reservationResponse, {
    "Reserva realizada com sucesso": (r) => r.status === 201,
    "Verifica código de reserva": (r) => r.json().code !== undefined,
  });

  let reservationCode = reservationResponse.json().code;

  // Cenário 3: Verificar reserva existente
  let reservationCheckResponse = http.get(`${BASE_URL}/reservations/${reservationCode}`);
  check(reservationCheckResponse, {
    "Reserva verificada com sucesso": (r) => r.status === 200,
    "Verifica código da reserva": (r) => r.json().code === reservationCode,
  });


  // Cenário 4: Verificar e marcar reserva como usada
  let reservationToVerifyCode = reservationCode; // Usando o código da reserva criada
  let reservationVerifyResponse = http.get(`${BASE_URL}/reservations/${reservationToVerifyCode}`);
  check(reservationVerifyResponse, {
    "Reserva 'Pendente' verificada com sucesso": (r) => r.status === 200 && r.json().used === false,
  });

  // Marcar reserva como usada
  let markAsUsedResponse = http.post(`${BASE_URL}/reservations/checkin/${reservationToVerifyCode}`);
  check(markAsUsedResponse, {
    "Reserva marcada como usada com sucesso": (r) => r.status === 200,
    "Verifica o status da reserva como 'Usada'": (r) => r.body.includes("Check-in successful"),
  });


  // Cenário 5: Deletar uma reserva existente
  let reservationsResponse = http.get(`${BASE_URL}/reservations`);
  check(reservationsResponse, {
    "Obter reservas com sucesso": (r) => r.status === 200,
  });

  let existingReservation = reservationsResponse.json()[0]; 
  let reservationToDeleteCode = existingReservation.code; 
  
  let deleteReservationResponse = http.del(`${BASE_URL}/reservations/${reservationToDeleteCode}`);
  check(deleteReservationResponse, {
    "Reserva deletada com sucesso": (r) => r.status === 200,
    "Verifica a mensagem de sucesso": (r) => r.body.includes("Reservation deleted successfully"),
  });


}
