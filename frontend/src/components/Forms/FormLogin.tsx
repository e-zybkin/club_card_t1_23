import { useForm, Controller } from "react-hook-form";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Password } from "primereact/password";

import { UserLogin } from "../../utils/interfaces";

import { classNames as cn } from "primereact/utils";
import styles from "./Forms.module.css";

interface props {
  handleLogin: (data: UserLogin) => Promise<void>;
}

export function FormLogin({ handleLogin }: props) {
  const defaultValues = {
    email: "",
    password: "",
  };

  const {
    control,
    formState: { errors, isValid },
    handleSubmit,
    reset,
  } = useForm({ defaultValues, mode: "onBlur" });

  const onSubmit = (data: UserLogin) => {
    handleLogin(data);
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
        <span className="p-float-label p-input-icon-right">
          <Controller
            name="email"
            control={control}
            rules={{
              required: "Обязательное поле Email.",
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
              minLength: 8,
              required: "Обязательное поле Пароль.",
            }}
            render={({ field, fieldState }) => (
              <Password
                id={field.name}
                {...field}
                toggleMask
                className={cn({
                  "p-invalid": fieldState.invalid,
                })}
                feedback={false}
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

      <Button
        type="submit"
        label="Войти"
        className="mt-2"
        disabled={!isValid}
      />
    </form>
  );
}
