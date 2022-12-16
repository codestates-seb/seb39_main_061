export const inputs = [
  {
    id: "email",
    name: "이메일",
    type: "email",
    placeholder: "이메일을 입력해주세요",
    errorMessage: "이메일 형식을 확인해주세요.",
    required: true,
  },
  {
    id: "password",
    name: "비밀번호",
    type: "password",
    placeholder: "비밀번호를 입력해주세요",
    errorMessage: "비밀번호는 8글자 이상이여야 합니다.",
    // pattern: ".{8,}",
    required: true,
  },

  {
    id: "ownerName",
    name: "대표 성명",
    type: "text",
    placeholder: "대표성명을 입력해주세요",
    errorMessage: "비밀번호는 8글자 이상이여야 합니다.",
    // pattern: ".{8,}",
    required: true,
  },
  {
    id: "businessName",
    name: "상호명",
    type: "text",
    placeholder: "상호명을 입력해주세요",
    errorMessage: "비밀번호는 8글자 이상이여야 합니다.",
    // pattern: ".{8,}",
    required: true,
  },

  {
    id: "phone",
    name: "전화번호",
    type: "text",
    placeholder: "휴대폰 번호를 입력해주세요",
    errorMessage: "비밀번호는 8글자 이상이여야 합니다.",
    // pattern: ".{8,}",
    required: true,
  },
];
