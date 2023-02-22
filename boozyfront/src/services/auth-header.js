export default function authHeader() {
  let user = JSON.parse(window.sessionStorage.getItem("vuex")).auth.user;

  console.log(user.token)
  if (user && user.token) {
    return { Authorization: 'Bearer ' + user.token }; // for Spring Boot back-end
    // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
  } else {
    return {};
  }
}
