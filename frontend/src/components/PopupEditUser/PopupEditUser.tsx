import { useContext, useEffect, useState } from "react";
import PopupWithForm from "../PopupWithForm/PopupWithForm";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";

import { UserUpdate } from "../../utils/interfaces";

interface props {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (userData: UserUpdate) => void;
}

function PopupEditUser({ isOpen, onClose, onSubmit }: props) {
  const [firstName, setFirstName] = useState<string>("");
  const [lastName, setLastName] = useState<string>("");
  const [middleName, setMiddleName] = useState<string>("");
  const [username, setUsername] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [birthday, setBirthday] = useState<string>("");

  const userContext = useContext(CurrentUserContext);

  const currentUser = userContext?.currentUser;

  useEffect(() => {
    setFirstName(currentUser?.firstName ?? "");
    setLastName(currentUser?.lastName ?? "");
    setMiddleName(currentUser?.middleName ?? "");
    setUsername(currentUser?.username ?? "");
    setEmail(currentUser?.email ?? "");
    setBirthday(
      currentUser?.dateOfBirth
        ? new Date(currentUser.dateOfBirth).toISOString().split("T")[0]
        : ""
    );
  }, [currentUser, isOpen]);

  // const handleSubmit = (e: React.FormEvent<HTMLFormElement>): void => {
  //   e.preventDefault();
  //   const formData = new FormData(e.currentTarget);
  //   const formProps = Object.fromEntries(formData.entries());

  //   const user = {
  //     username: formProps.username.toString(),
  //     email: formProps.email.toString(),
  //     firstName: formProps.firstname.toString(),
  //     lastName: formProps.lastname.toString(),
  //     middleName: formProps.middlename.toString(),
  //     birthday: new Date(formProps.birthday.toString()),
  //   };

  //   onSubmit(user);
  // };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    onSubmit({
      username: username,
      email: email,
      firstName: firstName,
      lastName: lastName,
      middleName: middleName,
      birthday: new Date(birthday),
    });
  };

  return (
    <PopupWithForm
      isOpen={isOpen}
      onClose={onClose}
      title="Ваши данные"
      onSubmit={handleSubmit}
      submitText="Изменить"
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
          value={username}
          onChange={(e) => setUsername(e.target.value)}
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
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <span className="input-error"></span>
      </div>

      <div className="input-box">
        <input
          required
          minLength={2}
          maxLength={40}
          type="text"
          placeholder="Фамилия"
          id="lastname-input"
          className="input"
          name="lastname"
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
        />
        <span className="input-error"></span>
      </div>

      <div className="input-box">
        <input
          required
          minLength={2}
          maxLength={40}
          type="text"
          placeholder="Имя"
          id="firstname-input"
          className="input"
          name="firstname"
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
        />
        <span className="input-error"></span>
      </div>

      <div className="input-box">
        <input
          required
          minLength={2}
          maxLength={40}
          type="text"
          placeholder="Отчество"
          id="middlename-input"
          className="input"
          name="middlename"
          value={middleName}
          onChange={(e) => setMiddleName(e.target.value)}
        />
        <span className="input-error"></span>
      </div>

      <div className="input-box">
        <input
          required
          type="date"
          placeholder="Дата рождения"
          id="birthday-input"
          className="input"
          name="birthday"
          value={birthday}
          onChange={(e) => setBirthday(e.target.value)}
        />
        <span className="input-error"></span>
      </div>
    </PopupWithForm>
  );
}

export default PopupEditUser;
