export const SET_CAPTCHA_IMG = "setCaptchaImg";
export const SAVE_CAPTCHA_TOKEN = "saveCaptchaToken";
export const SET_NEED_CAPTCHA = "setNeedCaptcha";
export const setCaptchaImg = (captchaImg) => ({type: SET_CAPTCHA_IMG, captchaImg});
export const saveCaptchaToken = (captchaToken) => ({type: SAVE_CAPTCHA_TOKEN, captchaToken});
export const setNeedCaptcha = (needCaptcha) => ({type: SET_NEED_CAPTCHA, needCaptcha});
