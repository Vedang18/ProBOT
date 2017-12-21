export function getUsers(data) {
  return {
    type: "GET_USERS",
    payload: { data }
  };
}