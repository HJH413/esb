import React, {useEffect, useState} from "react";
import clockImg from "../../assets/img/clock.png";
import {useLocation} from "react-router-dom";
import axios from "axios";
import LoadingBar from '../common/LoadingBar'; // 로딩 바 컴포넌트
const TimeCheckForm = () => {
    const location = useLocation();
    const {userNo, userName} = location.state || {}
    const [timeData, setTimeData] = useState([]);
    const [loading, setLoading] = useState(false); // 로딩 상태 관리

    const getTime = () => {
        axios.get(`/api/time/${userNo}`)
            .then(response => {
                setTimeData(response.data);
                setLoading(false);
            })
            .catch(error => {
                setLoading(false);
                alert(`조회 오류발생\n관리자에게 문의하세요.`);
                console.log(error);
            });
    }

    useEffect(() => {
        setLoading(true);
        getTime();
    }, []);


    const userData = {
        userNo: userNo
    }

    const timeCheckBtnClick = () => {
        if (timeData === '') {
            if (window.confirm(`출근하겠습니까?`)) {
                setLoading(true);
                axios.post(`/api/time`, userData)
                    .then(response => {
                        setTimeData(response.data);
                        setLoading(false);
                    })
                    .catch(error => {
                        setLoading(false);
                        alert(`출근 오류발생\n관리자에게 문의하세요.`);
                        console.log(error);
                    });
            }
        } else {
            if (window.confirm(`퇴근하겠습니까?`)) {
                setLoading(true);
                axios.patch(`/api/time/${timeData.timeCheckNo}`)
                    .then(response => {
                        setTimeData(response.data);
                        setLoading(false);
                    })
                    .catch(error => {
                        setLoading(false);
                        alert(`퇴근 오류발생\n관리자에게 문의하세요.`);
                        console.log(error);
                    });
            }
        }
    }

    return (
        <>
            {loading && <LoadingBar />}
            <div className={'mainDiv'}>
                <div className={'subDiv centerLine'}>
                    <div className={'textDiv'}>
                        <img src={clockImg} alt={'시계이미지'}/>
                        <span>출근시간</span>
                    </div>
                    <div className={'timeDiv'}>
                        <span>{timeData.startDate || `00년 00월 00일`}</span>
                        <span className={'timeSpan'}>{timeData.startTime || `00년 00월 00일`}</span>
                    </div>
                </div>
                <div className={'subDiv'}>
                    <div className={'textDiv'}>
                        <img src={clockImg} alt={'시계이미지'}/>
                        <span>퇴근시간</span>
                    </div>
                    <div className={'timeDiv'}>
                        <span>{timeData.endDate || `00년 00월 00일`}</span>
                        <span className={'timeSpan'}>{timeData.endTime || `00년 00월 00일`}</span>
                    </div>
                </div>
            </div>
            <div className={'mainDiv'}>
                <span>{userName}님 접속하였습니다.</span>
            </div>
            <div className={'buttonDiv'}>
                {timeData.endTime || null ? (
                    <h1>출퇴근 완료되었습니다.</h1>
                ) : (
                    <button className='btn' onClick={timeCheckBtnClick}>
                        <span className='btn-text'>{!timeData ? '출근' : '퇴근'}</span>
                    </button>
                )}
            </div>
        </>
    )
}

export default TimeCheckForm;

