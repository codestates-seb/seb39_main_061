import QRCode from 'qrcode'
import { useState } from 'react'
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function CreateQr() {
	const [url, setUrl] = useState('')
	const [qr, setQr] = useState('')
	const [formData, setFormData] = useState(new FormData)
	const [data, setData] = useState({ target: '', businessName: "test", dueDate: new Date() });

	const GenerateQRCode = () => {
		
		QRCode.toDataURL(url, {
			width: 320,
 			height: 320,
			color: {
				dark: '#000000',
				light: '#EEEEEEFF'
			}
		}, (err, url) => {
			if (err) return console.error(err)
			
			setQr(url)
			const form = new FormData;
			form.append("file", dataURLtoBlob(url), "qr.png");
		})
	}

	const	dataURLtoBlob = (dataurl) => {
			var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
					bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
			while(n--){
					u8arr[n] = bstr.charCodeAt(n);
			}
			return new Blob([u8arr], {type:mime});
	}

	const CancelQRCode = () => {
		setUrl('');
		setQr('');
	} 

	const saveQRCode = async () => {
		const formData = new FormData;
		formData.append("data", new Blob([JSON.stringify(data)], { type: "application/json" }))
		/**
		 * response를 받아와서 정상이라면 update api 호출
		 */
		// const response = await axios.post("http://localhost:8080/api/v1/qr-code", 
		// 	formData,
		// 	{
		// 		headers: {
		// 			Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5aXRza3lAbmF2ZXIuY29tIiwicm9sZSI6IlJPTEVfUkVTRVJWQVRJT04iLCJpYXQiOjE2NjMyMjkwOTQsImV4cCI6MTY2MzIzMjY5NH0.4NqshdzvtnPM7qqh0PwsjsHuHVHCoN3a0mIb_JHtJOplIX2cqr3IHDyLk2ihp3hS2V5ZBgVnRHhDuLkHwYl_0w",
		// 			"Content-Type": "multipart/form-data"
		// 		}
		// 	}
		// );



		/**
		 * response 데이터에 qr 코드 아이디가 날라올텐데 이를 이용하여 qr 코드 이미지 생성 후 update api 호출
		 * GnerateQRCode function 이용
		 */



		/**
		 * 이후 QR 코드 상세 페이지로 이동
		 */
	}

	return (
		<div className="app">
			<h1>QR 코드 생성</h1>
			{/* <img src={qr} /> */}
			<div>관리할 대상입니다.</div>
			<input 
				type="text"
				placeholder="대상명을 적어주세요"
				value={data.target}
				onChange={e => setData((prevState) => {return {...prevState, target: e.target.value }})} />
			<div>만료 기간을 선택해주세요.</div>				
			<Calendar onChange={e => setData((prevState) => {return {...prevState, dueDate: e }})} value={data.dueDate} />
			<button onClick={saveQRCode}>생 성</button>
			<button onClick={CancelQRCode}>취 소</button>
		</div>
	)
}

export default CreateQr