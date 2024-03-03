import React from 'react';
import "../assets/css/layout.scss";

const Layout = (props: {
    children : React.ReactNode
}) => {
    return (
        <div className={'bodyDiv'}>
            <div className={'titleDiv'}>
                ESB 출/퇴근 체크
            </div>
            <div className={'contentDiv'}>
                {props.children}
            </div>
        </div>
    )
}

export default Layout;