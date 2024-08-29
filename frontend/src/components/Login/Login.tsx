import AuthPage from "../AuthPage/AuthPage";
import { FormLogin } from "../Forms/FormLogin";
import { UserLogin } from "../../utils/interfaces";

interface props {
  handleLogin: (data: UserLogin) => Promise<void>;
}

function Login({ handleLogin }: props) {
  return (
    <AuthPage name="login" title="Вход" submitText="Войти" reg={false}>
      <FormLogin handleLogin={handleLogin} />
    </AuthPage>
  );
}

export default Login;
