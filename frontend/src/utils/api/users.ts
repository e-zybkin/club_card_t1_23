import { UserPrivileges, UserRoles } from "../enums";

export const BASE_URL = "http://localhost:8080/club-card/api/manager";

const HEADERS = {
  "Content-Type": "application/json",
};

const getToken = () => {
  return `Bearer ${localStorage.getItem("jwt")}`;
};

interface ErrorResponse {
  errors: string[];
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const getJson = async (response: Response): Promise<any> => {
  if (response.ok) {
    return response.json();
  }

  const err: ErrorResponse = await response.json();
  throw new Error(err.errors[0]);
};

export const getAllUsers = () =>
  fetch(`${BASE_URL}/get/users`, {
    method: "GET",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
  }).then(getJson);

export const changeRole = (data: {id: number; role: UserRoles}) => {
  return fetch(`${BASE_URL}/update/role/${data.id}`, {
    method: "PATCH",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
    body: JSON.stringify({
      role: data.role,
    }),
  }).then(getJson);
};

export const changePrivilege = (data: {id: number; privilege: UserPrivileges}) => {
  return fetch(`${BASE_URL}/update/privilege/${data.id}`, {
    method: "PATCH",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
    body: JSON.stringify({
      privilege: data.privilege,
    }),
  }).then(getJson);
};
  
export const deleteUser = (userId: number) =>
  fetch(`${BASE_URL}/delete/user/${userId}`, {
    method: "DELETE",
    headers: {
      ...HEADERS,
      Authorization: getToken(),
    },
  }).then(getJson);
