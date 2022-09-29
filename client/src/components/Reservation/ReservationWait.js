import {useState, useEffect} from "react"
import {useParams} from "react-router-dom"

function ReservationWait () {
    const { ids } = useParams();
    const [res, setRes] = useState([]);    

useEffect(() => {
    fetch(`http://localhost:3001/res?res=${ids}`)
    .then(res => {
        return res.json()
    })
    .then(data => {
        setRes(data);
    });
}, [res.id]);

    
function del() {
   
    if(window.confirm(`삭제하시겠습니까?`)){
        fetch(`http://localhost:3001/res?ids=${ids}` ,{
        method : 'DELETE',
        }) 
        .then((response) => response.json())
  .then((data) => console.log(data));
    }
}
    
    return (
    <>
    <ul>
        <li>번호</li>
        <li>이름</li>
        <li>연락처</li>
        <li>인원</li>
    </ul>
    
    
    <table>
        <tbody>
            {res.map(re => (
             <tr key={re.id}>   
                <td>{re.id}</td>
                <td>{re.name}</td>
                <td>{re.phone}</td>
                <td>{re.person}</td>
                <td><button onClick={del} className="btn_del">삭제</button></td>
            </tr>
            ))}
        </tbody>
    </table>
    </>
    )
}



export default ReservationWait