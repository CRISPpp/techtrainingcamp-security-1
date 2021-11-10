const BASE_URL = "http://localhost:8080";

function generateUrl(url) {
  return BASE_URL + url;
}

const ReqPath = {
  getCaptcha: generateUrl("/captcha"),
  needCaptcha: generateUrl("/captcha/need"),
  login: generateUrl("/user/login"),
  logout: generateUrl("/user/logout"),
  register: generateUrl("/user/register"),
  cancelAccount:generateUrl("/user/cancelAccount"),
}

export default ReqPath;