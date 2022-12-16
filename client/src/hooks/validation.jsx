export const validationInput = async () => {
  let regEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
  const phoneCheck = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;

  function strCheck(str, type) {
    const REGEX = {
      EMAIL: /\S+@\S+\.\S+/,
      PWD_RULE: /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/,
      NAME_RULE: /^[가-힣a-zA-Z]+$/,
    };
    if (type === "email") {
      return REGEX.EMAIL.test(str);
    } else if (type === "pwd") {
      return REGEX.PWD_RULE.test(str);
    } else if (type === "name") {
      return REGEX.NAME_RULE.test(str);
    } else {
      return false;
    }
  }

  if (email.length === 0) {
    setValidationMSG("이메일을 입력해주세요");
    return false;
  }
  if (regEmail.test(email) === false) {
    setValidationMSG("올바른 이메일 주소를 입력해주세요");
    return false;
  }

  const exist = await emailCheck(email); // 이메일 중복검사

  if (exist) {
    setValidationMSG("이미 가입되어 있는 이메일 입니다");
    return false;
  }

  if (password.length === 0) {
    setValidationMSG("비밀번호를 입력해주세요");
    return false;
  }
  if (strCheck(password, "pwd") === false) {
    setValidationMSG("비밀번호는 8~16자 영문+숫자+특수문자로 입력해주세요");
    return false;
  }
  if (password !== confirmPassword) {
    setValidationMSG("비밀번호가 일치하지 않습니다.");
    return false;
  }
  if (name.length === 0) {
    setValidationMSG("대표 성명을 입력해주세요");
    return false;
  }
  if (businessName.length === 0) {
    setValidationMSG("상호명을 입력해주세요");
    return false;
  }

  if (phone.length === 0) {
    setValidationMSG("휴대폰 번호를 입력해주세요");
    return false;
  }
  if (!phoneCheck.test(phone)) {
    setValidationMSG("휴대폰 번호를 정확히 입력해주세요");
    return false;
  }

  return true;
};
