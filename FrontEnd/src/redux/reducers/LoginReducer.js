import {SAVE_CAPTCHA_TOKEN, SET_CAPTCHA_IMG, SET_NEED_CAPTCHA} from "../actions/LoginAction";

const defaultState={
  captchaImg: {},
  captchaToken: '',
  needCaptcha: false
}

const LoginReducer = (state=defaultState,action)=>{
  let newState = JSON.parse(JSON.stringify(state));
  switch (action.type) {
    case SET_CAPTCHA_IMG:
      newState.captchaImg=action.captchaImg;
      return newState;
    case SAVE_CAPTCHA_TOKEN:
      newState.captchaToken=action.captchaToken;
      return newState;
    case SET_NEED_CAPTCHA:
      newState.needCaptcha=action.needCaptcha;
      return newState;
    default:
      return newState;
  }
}

export default LoginReducer;