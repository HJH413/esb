import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import LoadingBar from '../common/LoadingBar'; // 로딩 바 컴포넌트

const LoginForm = () => {
    const navigate = useNavigate();
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [loading, setLoading] = useState(false); // 로딩 상태 관리

    const onIdHandler = (event) => {
        setId(event.target.value);
    }

    const onPwHandler = (event) => {
        setPw(event.target.value);
    }

    const loginHandler = () => {
        if (id === '') {
            alert('아이디를 입력해주세요.');
            document.getElementById("esbId").focus();
            return;
        }

        if (pw === '') {
            alert('비밀번호를 입력해주세요.');
            document.getElementById("esbPw").focus();
            return;
        }
        
        const loginData = {
            userId: id,
            userPw: pw
        }

        setLoading(true);

        axios.post('/api/login', loginData)
            .then(response => {
                setLoading(false);
                if (response.data.success) {
                    navigate('/timeCheck', { state: { userNo: response.data.userNo, userName: response.data.userName } });
                } else {
                    alert(`아이디 또는 비밀번호를 확인하세요.`)
                }
            })
            .catch(error => {
                setLoading(false);
                alert(`로그인 오류발생\n관리자에게 문의하세요.`);
                console.log(error);
            });
    }

    return (
        <>
            {loading && <LoadingBar />}
            <div className={'inputDiv'}>
                <div className={'inputIdDiv'}>
                    <input id={"esbId"} type={'text'} onChange={onIdHandler} placeholder={'아이디'}/>
                </div>
                <div className={'inputPwDiv'}>
                    <input id={"esbPw"} type={'password'} onChange={onPwHandler} placeholder={'비밀번호'}/>
                </div>
            </div>
            <div className={'buttonDiv'}>
                <button className={'btn'} onClick={loginHandler}><span className={'btn-text'}>로그인</span></button>
            </div>
        </>
    )
}

export default LoginForm;

