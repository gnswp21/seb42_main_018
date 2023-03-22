import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';
import getGlobalState from '../util/authorization/getGlobalState';
import logo from '../assets/logo.svg';
import search from '../assets/icon_search.svg';
import mypage from '../assets/icon_mypage.svg';

const HeaderContainer = styled.header`
  box-sizing: border-box;
  margin: 0 auto;
  max-width: 500px;
  height: 50px;
  padding: 0px 20px;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background: var(--white);
  border-bottom: 1px solid var(--gray100);
  z-index: 100;
  
  display: flex;
  align-items: center;
  justify-content: space-between;
  
  img {
    height: 28px;
    margin-top: 6px;
    cursor: pointer;
  }
`;
const IconContainer = styled.div`
  img {
    height: 25px;
    margin-left: 6px;
    margin-top: 8px;
    cursor: pointer;
  }
`;

function Header() {
  const { isLogin } = getGlobalState();
  const navigate = useNavigate();

  const handleMyPageIcon = () => {
    if (isLogin) navigate('/mypage');
    else navigate('/login');
  };

  return (
    <HeaderContainer>
      <Link to='/'>
        <img src={logo} alt='소모전 로고' />
      </Link>
      <IconContainer>
        {/* TODO : 클릭시 검색창 모달 열리게 */}
        <button>
          <img src={search} alt='검색 아이콘' />
        </button>
        <button onClick={handleMyPageIcon}>
          <img src={mypage} alt='마이페이지 아이콘' />
        </button>
      </IconContainer>
    </HeaderContainer>
  );
}

export default Header;