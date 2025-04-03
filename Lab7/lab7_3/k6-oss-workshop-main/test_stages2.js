import http from "k6/http";
import { check, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
  stages: [
    { duration: "30s", target: 20 }, // Aumenta de 0 para 20 VUs em 30s
    { duration: "30s", target: 20 }, // Mantém 20 VUs por 30s
    { duration: "30s", target: 0 }, // Reduz de 20 para 0 VUs em 30s
  ],

  thresholds: {
    http_req_duration: ["p(95)<500"], // 95% das requisições devem ser respondidas em menos de 500ms
    http_req_failed: ["rate<0.01"], // Falha em menos de 1% das requisições
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

  check(res, {
    "Status code is 200": (r) => r.status === 200,
  });

  sleep(1); // Pausa para simular comportamento real
}
