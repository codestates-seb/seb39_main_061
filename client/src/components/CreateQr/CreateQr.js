import QRCode from 'qrcode'
import { useState } from 'react'



function CreateQr() {
	
	const [url, setUrl] = useState('')
	const [qr, setQr] = useState('')
	
	const GenerateQRCode = () => {
		
		QRCode.toDataURL(`${window.location.origin}/reservation?name=${url}`, {
			width: 320,
 			height: 320,
			color: {
				dark: '#000000',
				light: '#EEEEEEFF'
			}
		}, (err, url) => {
			if (err) return console.error(err)

			console.log(url)
			setQr(url)
		})
	}

	const CancelQRCode = () => {
		setUrl('');
		setQr('');
	} 


	return (
		<div className="app">
			<h1>QR 코드 생성</h1>
			<img src={qr} />
			<div>해당 상호명입니다.</div>
			<input 
				type="text"
				placeholder="대상명을 적어주세요"
				value={url}
				onChange={e => setUrl(e.target.value)} />
			<button onClick={GenerateQRCode}>생 성</button>
			<button onClick={CancelQRCode}>취 소</button>
		</div>
	)
}

export default CreateQr