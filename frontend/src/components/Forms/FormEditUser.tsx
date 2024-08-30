import { useContext } from "react";
import { useForm, Controller } from "react-hook-form";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Calendar } from "primereact/calendar";
import { classNames as cn } from "primereact/utils";
import { CurrentUserContext } from "../../contexts/CurrentUserContext";
import { UserUpdate } from "../../utils/interfaces";
import styles from "./Forms.module.css";

interface props {
  onUpdateUser: (userData: UserUpdate) => void;
}

export function FormEditUser({ onUpdateUser }: props) {
  const userContext = useContext(CurrentUserContext);
  const currentUser = userContext?.currentUser;

  const firstName = currentUser?.firstName;
  const lastName = currentUser?.lastName;
  const middleName = currentUser?.middleName;
  const birthday = currentUser?.dateOfBirth;
  const email = currentUser?.email;

  const defaultValues = {
    email,
    firstName,
    lastName,
    middleName,
    birthday,
  };

  const {
    control,
    formState: { errors, isValid, isDirty },
    handleSubmit,
    reset,
  } = useForm<UserUpdate>({ defaultValues, mode: "onChange" });

  const onSubmit = (data: UserUpdate) => {
    const preparedData: UserUpdate = {
      email: data.email || "",
      firstName: data.firstName || "",
      lastName: data.lastName || "",
      middleName: data.middleName || "",
      dateOfBirth: data.dateOfBirth || "",
    };
    onUpdateUser(preparedData);
    reset();
  };

  const getFormErrorMessage = (
    fieldName: keyof typeof errors
  ): JSX.Element | null =>
    errors[fieldName] ? (
      <small className="p-error">{errors[fieldName]?.message}</small>
    ) : null;

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className={`p-fluid ${styles.form}`}
    >
      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="firstName"
            control={control}
            rules={{
              required: "Поле не должно быть пустым",
              minLength: {
                value: 2,
                message: "Имя должно содержать не менее 2 символов",
              },
              maxLength: {
                value: 30,
                message: "Имя не должно содержать более 30 символов",
              },
            }}
            render={({ field, fieldState }) => (
              <InputText
                id={field.name}
                {...field}
                autoFocus
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label
            htmlFor="firstName"
            className={cn({ "p-error": errors.firstName })}
          >
            Имя
          </label>
        </span>
        {getFormErrorMessage("firstName")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="lastName"
            control={control}
            rules={{
              required: "Поле не должно быть пустым",
              minLength: {
                value: 2,
                message: "Фамилия должна содержать не менее 2 символов",
              },
              maxLength: {
                value: 30,
                message: "Фамилия не должна содержать более 30 символов",
              },
            }}
            render={({ field, fieldState }) => (
              <InputText
                id={field.name}
                {...field}
                autoFocus
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label
            htmlFor="lastName"
            className={cn({ "p-error": errors.lastName })}
          >
            Фамилия
          </label>
        </span>
        {getFormErrorMessage("lastName")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="middleName"
            control={control}
            rules={{
              required: "Поле не должно быть пустым",
              minLength: {
                value: 2,
                message: "Отчество должно содержать не менее 2 символов",
              },
              maxLength: {
                value: 30,
                message: "Отчество не должно содержать более 30 символов",
              },
            }}
            render={({ field, fieldState }) => (
              <InputText
                id={field.name}
                {...field}
                autoFocus
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label
            htmlFor="middleName"
            className={cn({ "p-error": errors.middleName })}
          >
            Отчество
          </label>
        </span>
        {getFormErrorMessage("middleName")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="email"
            control={control}
            rules={{
              required: "Обязательное поле Email.",
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                message: "Не корректный email. Например: example@email.ru",
              },
            }}
            render={({ field, fieldState }) => (
              <InputText
                id={field.name}
                {...field}
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label htmlFor="email" className={cn({ "p-error": errors.email })}>
            Email*
          </label>
        </span>
        {getFormErrorMessage("email")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="dateOfBirth"
            control={control}
            rules={{
              validate: {},
            }}
            render={({ field }) => <Calendar id={field.name} {...field} />}
          />
          <label
            htmlFor="birthday"
            className={cn({ "p-error": errors.dateOfBirth })}
          >
            Дата рождения*
          </label>
        </span>
        {getFormErrorMessage("dateOfBirth")}
      </div>

      <Button
        type="submit"
        label="Изменить данные"
        className="mt-2"
        disabled={!isValid || !isDirty}
      />
    </form>
  );
}
