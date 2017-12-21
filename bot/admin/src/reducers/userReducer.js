const initialState = {
  users: []
};

export default (state = initialState, action) => {
  switch (action.type) {
    case "GET_USERS_PENDING":
      break;
    case "GET_USERS_FULFILLED":
      break;
    case "GET_USERS_REJETED":
      break;
  }
  return state;
}