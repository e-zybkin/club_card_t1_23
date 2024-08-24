import AuthPage from "../AuthPage/AuthPage";
import { UserLogin } from "../../utils/interfaces";

interface props {
  handleLogin: (data: UserLogin) => Promise<void>;
}

function Login({ handleLogin }: props) {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const formProps = Object.fromEntries(formData.entries());

    const user = {
      username: formProps.username.toString(),
      password: formProps.password.toString(),
    };

    handleLogin(user);
  };

  return (
    <AuthPage
      name="login"
      title="Вход"
      submitText="Войти"
      onSubmit={handleSubmit}
      reg={false}
    >
      <div className="input-box">
        <input
          required
          minLength={2}
          maxLength={40}
          type="text"
          placeholder="Никнейм"
          id="username-input"
          className="input"
          name="username"
        />
        <span className="input-error"></span>
      </div>

      <div className="input-box">
        <input
          required
          minLength={2}
          maxLength={40}
          type="password"
          placeholder="Пароль"
          id="password-input"
          className="input"
          name="password"
        />
        <span className="input-error"></span>
      </div>
    </AuthPage>
  );
}

export default Login;
