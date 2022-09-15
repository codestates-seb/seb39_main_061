import { useState, useEffect } from 'react';
import axios from 'axios';
// import { useDispatch, useSelector } from 'react-redux';

const ProfileImgUpload = (submitImg) => {
  // const dispatch = useDispatch();
  // const imagefile = useSelector(state => state.ProfileImg)
  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "img/default_image.png",
  });

  let inputRef;

  // 이미지 preview 함수
  const saveImage = (e) => {
    e.preventDefault();
    const fileReader = new FileReader();
    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0])
    }
    fileReader.onload = () => {
      setImage(
        {
          image_file: e.target.files[0],
          preview_URL: fileReader.result
        }
      )
    }
  }

  // 이미지 upload 함수
  const sendImageToServer = async () => {
    if (image.image_file) {
      const formData = new FormData()
      formData.enctype = "multipart/form-data"
      const profileFormData = {
        "password": "test",
        "sectorId": 1,
        "service": ["RESERVATION"]
      }
      formData.append('file', image.image_file)
      formData.append("data", 
      new Blob([JSON.stringify(profileFormData)], { type: "application/json" }));
      console.log(formData.get('file'));
      const profileData = await axios.post(
        'http://localhost:8080/api/v1/members/profile',
        formData,
        {
          headers: {
            Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5aXRza3lAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfUkVTRVJWQVRJT04iLCJpYXQiOjE2NjMyNDcyNTEsImV4cCI6MTY2MzI1MDg1MX0.j_er63uI_lgy_MQW--p5UYQ8r0wjEyLP8m7Jl453agAWpsss62jm1HuIRak2y1O67977mXLmFciaKus2qYY-rA",
            "Content-Type": "multipart/form-data"
          }
        });
      console.log(profileData);
      alert("서버에 이미지 등록이 완료되었습니다!");
      setImage({
        image_file: "",
        preview_URL: "img/default_image.png",
      });
    }
    else {
      alert("사진을 등록하세요!")
    }
  }

  return (
    <div>
      <input
        type="file"
        accept="image/*"
        onChange={saveImage}
        // 클릭할 때 마다 file input의 value를 초기화 하지 않으면 버그가 발생할 수 있다
        // 사진 등록을 두개 띄우고 첫번째에 사진을 올리고 지우고 두번째에 같은 사진을 올리면 그 값이 남아있음!
        onClick={(e) => e.target.value = null}
        ref={refParam => inputRef = refParam}
        style={{ display: "none" }}
      />
      <div>
        <img src={image.preview_URL} />
      </div>
      <div>
        <button onClick={() => inputRef.click()}>
          Preview
        </button>
        <button onClick={sendImageToServer}>
          Upload
        </button>
      </div>

    </div>
  );
}

export default ProfileImgUpload;