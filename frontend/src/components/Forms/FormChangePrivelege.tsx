import { useEffect } from "react";
import { useForm, Controller } from "react-hook-form";

import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";
import { classNames as cn } from "primereact/utils";

import styles from "./Forms.module.css";
import { User } from "../../utils/interfaces";
import { UserPrivileges } from "../../utils/enums";

interface props {
  user: User;
  onChangePrivilege: (id: number, newPrivilege: UserPrivileges) => void;
}

interface FormData {
  privilege: {
    name: string;
    val: UserPrivileges;
  };
}

export function FormChangePrivelege({ user, onChangePrivilege }: props) {
  const privilege = [
    { name: "Стандарт", val: UserPrivileges.standart },
    { name: "Повышенный", val: UserPrivileges.high },
    { name: "VIP", val: UserPrivileges.vip },
  ];

  const defaultValues = {
    privilege: {
      name: `${
        user.privilege === UserPrivileges.vip
          ? "VIP"
          : user.privilege === UserPrivileges.high
          ? "Повышенный"
          : "Стандарт"
      }`,
      val: user.privilege,
    },
  };

  const {
    control,
    formState: { errors, isValid, isDirty },
    handleSubmit,
    reset,
  } = useForm({ defaultValues });

  const onSubmit = (data: FormData) => {
    onChangePrivilege(user.id, data.privilege.val);

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
      privilege: {
        name:
          user.privilege === UserPrivileges.vip
            ? "VIP"
            : user.privilege === UserPrivileges.high
            ? "Повышенный"
            : "Стандарт",
        val: user.privilege,
      },
    });
  }, [user.privilege, reset]);

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className={`p-fluid ${styles.roleForm}`}
    >
      <div>
        <Controller
          name="privilege"
          control={control}
          render={({ field, fieldState }) => (
            <Dropdown
              id={field.name}
              value={field.value || ""}
              optionLabel="name"
              placeholder="Выберите привилегию"
              options={privilege}
              focusInputRef={field.ref}
              onChange={(e) => field.onChange(e.value)}
              className={cn({ "p-invalid": fieldState.error })}
            />
          )}
        />
        {getFormErrorMessage("privilege")}
      </div>

      <Button
        type="submit"
        label="Изменить привилегию"
        disabled={!isValid || !isDirty}
      />
    </form>
  );
}
