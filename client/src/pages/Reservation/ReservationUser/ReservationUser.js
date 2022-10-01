import {useState, useEffect, useRef} from "react"
import axios from 'axios'
import food from "../ReservationUser/food.png"
import logo from "../ReservationUser/Asset_2.png"
import styles from "./ReservationUser.module.css";
import {Link} from "react-router-dom"




function ReservationUser () {
    const [num, setNum] = useState('');
    const phoneRef = useRef();
    const [res, setRes] = useState([]);  
    
    

    const url = "http://localhost:8080/business/1/reservation/qr-code/1?page=1&size=10"

    
   
    
    
    
    const axiosData = async() => {
        try{  
          axios.get(url, {
            
            })
           .then(function(response) {
              setRes(response.data);
              console.log(res.data)
             
          })
      }
          catch(err) {
          console.log("Error >>", err);
        }
      }




useEffect(() => {
        axiosData();
       
        setInterval(() => {
            axiosData();
        }, 60000);
        
}, []);

const handlePhone = (e) => {
    setNum(e.target.value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')); 
    };

const onSubmitHandler = (e) => {
       
     const name  =  e.target.name.value;
     const phone =  e.target.phone.value;
     const count =  e.target.count.value;
    
    if(phone.includes('*')===true || phone.length === 13){
        axios.post('http://localhost:8080/business/1/reservation/qr-code/1', {
            
            name,
            phone,
            count
           
        }).then (() => axiosData());
        alert(`${name}님의 예약이 등록되었습니다.`)  
    }
    else {
        alert(`연락처 11자리를 입력하세요 (-제외)`)  
    }
     
   
};

    
   
    return (
        <>
        
        <img className={styles.food} src={food} />   
        <div className={styles.pages}>
        
            <div className={styles.userhaed}>
                <div className={styles.address}>
                    <div>덕이네 불족발</div>
                    <div>서울시 강동구 새파람길 34</div>
                </div>
            <Link to ="/review-user" className={styles.link}>리뷰쓰기</Link>
             </div>
       <table className={styles.table}>
            <thead className={styles.thead}>
                <tr>
                    <th className={styles.th}>번호</th>
                    <th className={styles.th}>이름</th>
                    <th className={styles.th}>연락처</th>
                    <th className={styles.th}>인원</th>
          
                </tr>
            </thead>
            <tbody className={styles.tbody}>
  {res.data && res.data.map((re) => {
    return (
      <tr className={styles.tr} key={re.reservationId}>
        <td className={styles.td}>{re.reservationId}</td>
        <td className={styles.td}>{re.name}</td>
        <td className={styles.td}>{re.phone}</td>
        <td className={styles.td}>{re.count}</td>
       
      </tr>
    );
  })}
</tbody>

        </table>
    
        <div className={styles.upform}>
        <form className={styles.form} onSubmit={onSubmitHandler}>
            
            <div className={styles.div}>
                <input className={styles.input} type="text" name="name" required="required" placeholder=" " />
                <label for="" className={styles.label}>이름</label>
            </div>
            <div className={styles.div}>
                <input className={styles.input} type="tel" name="phone" required="required" value={num} ref={phoneRef} placeholder=" " onChange={handlePhone} />
                <label for="" className={styles.label}>연락처 -제외</label>
                </div>
            <div className={styles.div}>
                <input className={styles.input} type="number" name="count" required="required" placeholder=" " />
                <label for="" className={styles.label}>인원 수</label>
            </div>
            <div>
            <button className={styles.button_res}type="submit">예약</button>
            <img className={styles.logo} src={logo} />
            </div>   
        </form>
        </div> 
             
        </div>
        </>
        )
    }
    



export default ReservationUser