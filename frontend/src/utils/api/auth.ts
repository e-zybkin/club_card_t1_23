import { UserLogin, UserRegister, UserUpdate } from "../interfaces";

export const BASE_URL = "http://localhost:8080/club-card/api";

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

export const register = ({ password, email, username }: UserRegister) => {
  return fetch(`${BASE_URL}/auth/signup`, {
    method: "POST",
    headers: HEADERS,
    body: JSON.stringify({ password, email, username }),
  }).then(getJson);
};

export const authorize = ({ username, password } : UserLogin) => {
  return fetch(`${BASE_URL}/auth/login`, {
    method: "POST",
    headers: HEADERS,
    body: JSON.stringify({ username, password }),
  }).then(getJson);
};

export const getMyInfo = () => {
  return fetch(`${BASE_URL}/users`, {
    method: "GET",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
  }).then(getJson);
};

export const updUserData = (data: UserUpdate) => {
  return fetch(`${BASE_URL}/users`, {
    method: "PATCH",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
    body: JSON.stringify({
      firstName: data.firstName,
      lastName: data.lastName,
      middleName: data.middleName,
      username: data.username,
      email: data.email,
      dateOfBirth: data.birthday,
    }),
  }).then(getJson);
};
