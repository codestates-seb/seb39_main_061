import QRCode from 'qrcode'
import { useState } from 'react'
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import axios from 'axios';

function CreateQr() {
	const [url, setUrl] = useState('')
	const [qr, setQr] = useState('')
	const [formData, setFormData] = useState(new FormData)
	const [body, setBody] = useState({ target: '', businessName: "test", dueDate: new Date(), qrType: "reservation" });

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
		
		const {data: {data, message}, status} = await axios.post("http://localhost:8080/api/v1/qr-code/reservation", 
			body,
			{
				headers: {
					Authorization: `Bearer ${localStorage.getItem("token")}`,
				}
			}
		);
		console.log(status)
		console.log(message)
		if(message === "CREATED") {
			/**
			 * response를 받아와서 정상이라면 update api 호출
		 */
			// TO-DO: 추후 URL 변경 필요, 세부적인 에러 처리 필요!
			console.log(data)
			console.log("저장 성공 " + url)
			QRCode.toDataURL(`${window.location.origin}/reservation/${data.qrCodeId}?target=${data.target}`, {
				width: 320,
				 height: 320,
				color: {
					dark: '#000000',
					light: '#EEEEEEFF'
				}
			}, (err, url) => {
				if (err) return console.error(err)
				
				setQr(url)
				const formData = new FormData;
				formData.append("data", new Blob([JSON.stringify(body)], { type: "application/json" }))
				formData.append("file", dataURLtoBlob(url), "qr.png")
				console.log(dataURLtoBlob(url))
				const response = axios.post(`http://localhost:8080/api/v1/qr-code/reservation/${data.qrCodeId}/update`, 
					formData,
					{
						headers: {
							Authorization: `Bearer ${localStorage.getItem("token")}`,
							"Content-Type": "multipart/form-data"
						}
					}
				).then((response) => {
					/**
					 * 이후 QR 코드 상세 페이지로 이동
					 */
					console.log(response);
				});
			})
		}
		
	}

	return (
		<div className="app">
			<h1>QR 코드 생성</h1>
			<img src={qr} />
			<div>관리할 대상입니다.</div>
			<input 
				type="text"
				placeholder="대상명을 적어주세요"
				value={body.target}
				onChange={e => setBody((prevState) => {return {...prevState, target: e.target.value }})} />
			<div>만료 기간을 선택해주세요.</div>				
			<Calendar onChange={e => setBody((prevState) => {return {...prevState, dueDate: new Date(e - new Date().getTimezoneOffset() * 60000) }})} value={body.dueDate} />
			<button onClick={saveQRCode}>생 성</button>
			<button onClick={CancelQRCode}>취 소</button>
		</div>
	)
}

export default CreateQr