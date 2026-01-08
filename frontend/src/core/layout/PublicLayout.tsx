import React from 'react';
import { Outlet } from 'react-router-dom';

const PublicLayout = () => {
  return (
    <div>
      <header>
        <h1>Public Layout</h1>
      </header>
      <main>
        <Outlet />
      </main>
      <footer>Footer content</footer>
    </div>
  );
};

export default PublicLayout;
