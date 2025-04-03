import http from "k6/http";
import { check, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:3333";

export const options = {
  stages: [
    { duration: "5s", target: 20 }, // Aumenta de 0 para 20 usuários virtuais em 5s
    { duration: "10s", target: 20 }, // Mantém 20 usuários por 10s
    { duration: "5s", target: 0 }, // Reduz de 20 para 0 usuários em 5s
  ],
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

  // Verifica se a resposta tem status 200
  check(res, {
    "Status code is 200": (r) => r.status === 200,
  });

  sleep(1); // Pausa para simular comportamento realista
}
