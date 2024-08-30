import { useEffect } from "react";
import { useForm, Controller } from "react-hook-form";

import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";
import { classNames as cn } from "primereact/utils";

import styles from "./Forms.module.css";
import { User } from "../../utils/interfaces";
import { UserRoles } from "../../utils/enums";

interface props {
  user: User;
  onChangeRole: (id: number, newRole: UserRoles) => void;
}

interface FormData {
  role: {
    name: string;
    val: UserRoles;
  };
}

export function FormChangeRole({ user, onChangeRole }: props) {
  const roles = [
    { name: "Пользователь", val: UserRoles.member },
    { name: "Администратор", val: UserRoles.manager },
  ];

  const defaultValues = {
    role: {
      name: `${
        user.role === UserRoles.manager ? "Администратор" : "Пользователь"
      }`,
      val: user.role,
    },
  };

  const {
    control,
    formState: { errors, isValid, isDirty },
    handleSubmit,
    reset,
  } = useForm({ defaultValues });

  const onSubmit = (data: FormData) => {
    onChangeRole(user.id, data.role.val);

    reset();
  };

  const getFormErrorMessage = (
    fieldName: keyof typeof errors
  ): JSX.Element | null =>
    errors[fieldName] ? (
      <small className="p-error">{errors[fieldName]?.message}</small>
    ) : null;

  useEffect(() => {
    reset({
      role: {
        name:
          user.role === UserRoles.manager ? "Администратор" : "Пользователь",
        val: user.role,
      },
    });
  }, [user.role, reset]);

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className={`p-fluid ${styles.roleForm}`}
    >
      <div>
        <Controller
          name="role"
          control={control}
          render={({ field, fieldState }) => (
            <Dropdown
              id={field.name}
              value={field.value || ""}
              optionLabel="name"
              placeholder="Выберите роль"
              options={roles}
              focusInputRef={field.ref}
              onChange={(e) => field.onChange(e.value)}
              className={cn({ "p-invalid": fieldState.error })}
            />
          )}
        />
        {getFormErrorMessage("role")}
      </div>

      <Button
        type="submit"
        label="Изменить роль"
        disabled={!isValid || !isDirty}
      />
    </form>
  );
}
