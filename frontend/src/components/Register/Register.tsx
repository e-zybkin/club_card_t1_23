import AuthPage from "../AuthPage/AuthPage";
import { UserRegister } from "../../utils/interfaces";

interface props {
  handleRegister: (data: UserRegister) => Promise<void>;
}

function Register({ handleRegister }: props) {
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const formProps = Object.fromEntries(formData.entries());

    const user = {
      username: formProps.username.toString(),
      email: formProps.email.toString(),
      password: formProps.password.toString(),
    };

    handleRegister(user);
  };

  return (
    <AuthPage
      name="register"
      title="Регистрация"
      submitText="Зарегистрироваться"
      onSubmit={handleSubmit}
      reg={true}
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
          type="text"
          placeholder="Почта"
          id="email-input"
          className="input"
          name="email"
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

export default Register;
