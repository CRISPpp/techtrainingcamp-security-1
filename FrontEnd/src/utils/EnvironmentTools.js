
export const getHash=(str)=>{
  if (str==null)  str="";
  var hash  =   1315423911,i,ch;
  for (i = str.length - 1; i >= 0; i--) {
    ch = str.charCodeAt(i);
    hash ^= ((hash << 5) + ch + (hash >> 2));
  }
  return  (hash & 0x7FFFFFFF);
}

export const getDeviceId=()=>{
  let id="";
  id+=getHash(navigator.appName);
  id+=getHash(navigator.appVersion);
  id+=getHash(navigator.platform);
  id+=getHash(navigator.userAgent);
  let lim=navigator.plugins.length>10?10:navigator.plugins.length;
  for (let i = 0; i < lim; i++) {
    id+=getHash(navigator.plugins[i].name);
  }
  return "deviceId"+id;
}

export const getIp=()=>{
  return "ip"+getDeviceId();
}