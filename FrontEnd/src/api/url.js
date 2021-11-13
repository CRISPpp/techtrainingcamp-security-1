export const BE_BASE_URL = "http://119.91.73.146:8080/AccountSystem";
export const FE_BASE_URL = "http://119.91.73.146/account"
function generateUrl(url) {
  return BE_BASE_URL + url;
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