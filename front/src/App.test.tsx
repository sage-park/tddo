import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  render(<App />);

  // App이 렌더링 되면 /login 으로 리다이렉트 한다.

  expect(screen.getByText('login')).toBeInTheDocument();

});
