import AuthPage from "../AuthPage/AuthPage";
import { FormRegistration } from "../Forms/FormRegistration";
import { UserRegister } from "../../utils/interfaces";

interface props {
  handleRegister: (data: UserRegister) => Promise<void>;
}

function Register({ handleRegister }: props) {
  return (
    <AuthPage
      name="register"
      title="Регистрация"
      submitText="Зарегистрироваться"
      reg={true}
    >
      <FormRegistration handleRegister={handleRegister} />
    </AuthPage>
  );
}

export default Register;
