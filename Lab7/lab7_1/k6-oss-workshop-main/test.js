import http from "k6/http";
import { check, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
  vus: 1, // Número de usuários virtuais
  iterations: 1, // Apenas uma execução para o teste simples
};

export default function () {
  let restrictions = {
    maxCaloriesPerSlice: 500,
    mustBeVegetarian: false,
    excludedIngredients: ["pepperoni"],
    excludedTools: ["knife"],
    maxNumberOfToppings: 6,
    minNumberOfToppings: 2,
  };
  let res = http.post(`${BASE_URL}/api/pizza`, JSON.stringify(restrictions), {
    headers: {
      "Content-Type": "application/json",
      "X-User-ID": 23423,
      "Authorization": "token abcdef0123456789", // Token adicionado
    },
  });
  // Verifica se a resposta é um sucesso (status 200)
  check(res, {
    "Status code is 200": (r) => r.status === 200,
  });

  // Exibe o resultado no terminal
  if (res.status === 200) {
    console.log(`${res.json().pizza.name} (${res.json().pizza.ingredients.length} ingredients)`);
  } else {
    console.log(`Request failed with status ${res.status}`);
  }

  sleep(1); // Pequeno intervalo antes de encerrar

}