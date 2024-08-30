import { useForm, Controller } from "react-hook-form";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { Password } from "primereact/password";
import { Divider } from "primereact/divider";
import { Checkbox } from "primereact/checkbox";
import { UserRegister } from "../../utils/interfaces";
import { classNames as cn } from "primereact/utils";
import styles from "./Forms.module.css";

interface props {
  handleRegister: (data: UserRegister) => Promise<void>;
}

export function FormRegistration({ handleRegister }: props) {
  const defaultValues = {
    firstName: "",
    lastName: "",
    middleName: "",
    email: "",
    password: "",
    confirmPassword: "",
    confirmPrivacy: false,
  };

  const {
    control,
    formState: { errors, isValid },
    handleSubmit,
    reset,
    watch,
  } = useForm({ defaultValues, mode: "onChange" });

  const onSubmit = (data: {
    firstName: string;
    lastName: string;
    middleName: string;
    email: string;
    password: string;
    confirmPassword: string;
    confirmPrivacy: boolean;
  }) => {
    handleRegister({
      firstName: data.firstName,
      lastName: data.lastName,
      middleName: data.middleName,
      email: data.email,
      password: data.password,
    });
    reset();
  };

  const getFormErrorMessage = (
    fieldName: keyof typeof errors
  ): JSX.Element | null =>
    errors[fieldName] ? (
      <small className="p-error">{errors[fieldName]?.message}</small>
    ) : null;

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
            name="firstName"
            control={control}
            rules={{
              required: "Обязательное поле Имя.",
              minLength: {
                value: 2,
                message: "Имя должно содержать не менее 2 символов",
              },
              maxLength: {
                value: 20,
                message: "Имя не должно содержать более 20 символов",
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
            className={cn({ "p-error": !!errors.firstName })}
          >
            Имя*
          </label>
        </span>
        {getFormErrorMessage("firstName")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label p-input-icon-right">
          <Controller
            name="lastName"
            control={control}
            rules={{
              required: "Обязательное поле Фамилия.",
              minLength: {
                value: 2,
                message: "Фамилия должна содержать не менее 2 символов",
              },
              maxLength: {
                value: 20,
                message: "Фамилия не должна содержать более 20 символов",
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
            className={cn({ "p-error": !!errors.lastName })}
          >
            Фамилия*
          </label>
        </span>
        {getFormErrorMessage("lastName")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label p-input-icon-right">
          <Controller
            name="middleName"
            control={control}
            rules={{
              required: "Обязательное поле Отчество.",
              minLength: {
                value: 2,
                message: "Отчество должно содержать не менее 2 символов",
              },
              maxLength: {
                value: 20,
                message: "Отчество не должно содержать более 20 символов",
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
            className={cn({ "p-error": !!errors.middleName })}
          >
            Отчество*
          </label>
        </span>
        {getFormErrorMessage("middleName")}
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
                message: "Некорректный email. Например: example@email.ru",
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
            // rules={{
            //   required: "Обязательное поле Пароль.",
            //   maxLength: {
            //     value: 40,
            //     message: "Максимальная длина 40 символов.",
            //   },
            //   pattern: {
            //     value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,42})/,
            //     message: "Некорректный пароль",
            //   },
            // }}
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
            className={cn({ "p-error": !!errors.password })}
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
            className={cn({ "p-error": !!errors.confirmPassword })}
          >
            Повторите пароль*
          </label>
        </span>
        {getFormErrorMessage("confirmPassword")}
      </div>

      <div className={styles.field}>
        <span className="p-float-label">
          <Controller
            name="confirmPrivacy"
            control={control}
            rules={{ required: "Необходимо согласие." }}
            render={({ field, fieldState }) => (
              <div className={styles.checkbox}>
                <Checkbox
                  id={field.name}
                  {...field}
                  checked={field.value}
                  className={cn({
                    "p-invalid": fieldState.invalid,
                  })}
                />
                <p className={styles.privacy}>
                  Нажимая кнопку "Зарегистрироваться", я даю своё согласие на
                  обработку моих персональных данных, в соответствии с
                  Федеральным законом{" "}
                  <a href="http://pravo.gov.ru/proxy/ips/?docbody&nd=102108261">
                    №152-ФЗ "О персональных данных"
                  </a>
                </p>
              </div>
            )}
          />
        </span>
        {getFormErrorMessage("confirmPrivacy")}
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
