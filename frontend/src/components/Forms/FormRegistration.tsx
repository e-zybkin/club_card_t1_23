import { useForm, Controller } from "react-hook-form";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Password } from "primereact/password";
import { Divider } from "primereact/divider";
import { UserRegister } from "../../utils/interfaces";
import { classNames as cn } from "primereact/utils";
import styles from "./Forms.module.css";

interface props {
  handleRegister: (data: UserRegister) => Promise<void>;
}

export function FormRegistration({ handleRegister }: props) {
  const defaultValues = {
    initials: "",
    email: "",
    password: "",
    confirmPassword: "",
  };

  const {
    control,
    formState: { errors, isValid },
    handleSubmit,
    reset,
    watch,
  } = useForm({ defaultValues, mode: "onChange" });

  const onSubmit = (data: {
    initials: string;
    email: string;
    password: string;
    confirmPassword: string;
  }) => {
    const FIO = data.initials.split(" ");
    handleRegister({
      firstName: FIO[1],
      lastName: FIO[0],
      middleName: FIO[2],
      email: data.email,
      password: data.password,
    });
    reset();
  };

  const getFormErrorMessage = (name: string) =>
    errors[name] && <small className="p-error">{errors[name].message}</small>;

  const passwordHeader = <h4>Введите пароль</h4>;
  const passwordFooter = (
    <>
      <Divider />
      <p className="mt-2">Правила для пароля:</p>
      <ul className="pl-2 ml-2 mt-0" style={{ lineHeight: "1.5" }}>
        <li>Хотя бы 1 маленькая буква английского алфавита</li>
        <li>Хотя бы 1 заглавная буква английского алфавита</li>
        <li>Хотя бы одна цифра</li>
        <li>Минимум 8 символов</li>
      </ul>
    </>
  );

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className={`p-fluid ${styles.form}`}
    >
      <div className={styles.field}>
        <span className="p-float-label p-input-icon-right">
          <Controller
            name="initials"
            control={control}
            rules={{
              required: "Обязательное поле ФИО.",
              minLength: {
                value: 20,
                message: "ФИО должно содержать не менее 20 символов",
              },
              maxLength: {
                value: 50,
                message: "ФИО не должно содержать более 50 символов",
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
            htmlFor="initials"
            className={cn({ "p-error": !!errors.name })}
          >
            ФИО*
          </label>
        </span>
        {getFormErrorMessage("initials")}
      </div>
      <div className={styles.field}>
        <span className="p-float-label p-input-icon-right">
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
                autoFocus
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label htmlFor="email" className={cn({ "p-error": !!errors.email })}>
            Email*
          </label>
        </span>
        {getFormErrorMessage("email")}
      </div>
      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="password"
            control={control}
            rules={{
              required: "Обязательное поле Пароль.",
              maxLength: {
                value: 40,
                message: "Максимальная длина 40 символа.",
              },
              pattern: {
                value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,42})/,
                message: "Не корректный пароль",
              },
            }}
            render={({ field, fieldState }) => (
              <Password
                id={field.name}
                {...field}
                toggleMask
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
                header={passwordHeader}
                footer={passwordFooter}
              />
            )}
          />
          <label
            htmlFor="password"
            className={cn({ "p-error": errors.password })}
          >
            Пароль*
          </label>
        </span>
        {getFormErrorMessage("password")}
      </div>
      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="confirmPassword"
            control={control}
            rules={{
              required: "Обязательное поле.",
              validate: (value) =>
                watch("password") === value || "Пароли должны совпадать!",
            }}
            render={({ field, fieldState }) => (
              <Password
                id={field.name}
                {...field}
                feedback={false}
                toggleMask
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
              />
            )}
          />
          <label
            htmlFor="confirmPassword"
            className={cn({ "p-error": errors.confirmPassword })}
          >
            Повторите пароль*
          </label>
        </span>
        {getFormErrorMessage("confirmPassword")}
      </div>

      <Button
        type="submit"
        label="Зарегистрироваться"
        className="mt-2"
        disabled={!isValid}
      />
    </form>
  );
}
