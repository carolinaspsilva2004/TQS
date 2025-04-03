import http from "k6/http";
import { check, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
  stages: [
    { duration: "30s", target: 20 }, // Ramp-up: 0 → 20 VUs
    { duration: "30s", target: 20 }, // Estável: 20 VUs
    { duration: "30s", target: 0 },  // Ramp-down: 20 → 0 VUs
  ],

  thresholds: {
    "http_req_duration{expected_response:true}": ["p(95)<1100"], // 95% das requisições < 1.1s
    "http_req_failed": ["rate<0.01"], // Falha < 1% das requisições
    "checks": ["rate>0.98"], // Pelo menos 98% dos checks devem passar
  },
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
      "Authorization": "token abcdef0123456789",
    },
  });

  let checkRes = check(res, {
    "Status code is 200": (r) => r.status === 200,
    "Response contains pizza name": (r) => r.json().pizza?.name !== undefined,
    "Response body size is under 1KB": (r) => r.body.length < 1024, // Verifica se o corpo da resposta é menor que 1KB
  });

  // Log para depuração (opcional)
  if (!checkRes) {
    console.error(`❌ Falha no teste! Status: ${res.status}, Body: ${res.body}`);
  }

  sleep(1); // Simular comportamento real de usuário
}
