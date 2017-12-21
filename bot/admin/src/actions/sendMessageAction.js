export function sendMessage(data) {
  return {
    type: "SEND_MESSAGE",
    payload: data
  }
}