import { CardColors, CardTemplates } from "../enums";

export const BASE_URL = "http://localhost:8080/club-card/api/card";

const HEADERS = {
  "Content-Type": "application/json",
};

const getToken = () => {
  return `Bearer ${localStorage.getItem("jwt")}`;
};

const getJson = (response) => {
  if (response.ok) {
    return response.json();
  }
  return response.json().then((err) => {
    throw new Error(err.message);
  });
};

export const createCard = (colorName: keyof typeof CardColors, templateName: keyof typeof CardTemplates ) => {
  return fetch(`${BASE_URL}/create`, {
    method: "POST",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
    body: JSON.stringify({ colour: colorName, pattern: templateName }),
  }).then(getJson);
};

export const getCardInfo = () => {
  return fetch(`${BASE_URL}`, {
    method: "GET",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    }
  }).then(getJson);
}
