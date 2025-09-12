import React from 'react';
// import { ProfileDropdown } from './ProfileDropdown';
// import './assets/styles.css';
import Logo from '../assets/logo.png';
import CartIcon from './CartIcon';

export default function Header({ cartCount, username }) {
  return (
    <header className="header">
      <div className="header-content">
        <Logo />
        <div className="header-actions">
          <CartIcon cartCount={0} />
          {/* <ProfileDropdown username={username} /> */}
        </div>
      </div>
    </header>
  );
}