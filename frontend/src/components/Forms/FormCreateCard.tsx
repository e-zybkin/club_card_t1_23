import { useState } from "react";
import logo from "../../assets/cards/card-logo.png";
import name from "../../assets/cards/card-name.png";

import { Button } from "primereact/button";
import { RadioButton, RadioButtonChangeEvent } from "primereact/radiobutton";

import { CardTemplates, CardColors } from "../../utils/enums";

import styles from "./Forms.module.css";

interface props {
  onSubmit: (
    colorName: keyof typeof CardColors,
    templateName: keyof typeof CardTemplates
  ) => void;
}

function FormCreateCard({ onSubmit }: props) {
  const [color, setColor] = useState<CardColors>(CardColors.BLUE);
  const [template, setTemplate] = useState<CardTemplates>(CardTemplates.FULL);
  const [colorName, setColorName] = useState<keyof typeof CardColors>("BLUE");
  const [templateName, setTemplateName] =
    useState<keyof typeof CardTemplates>("FULL");

  const templates = Object.entries(CardTemplates);
  const colors = Object.entries(CardColors);

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        onSubmit(colorName, templateName);
      }}
      className={styles.form}
    >
      <h3 className={styles.headers}>Выберите цвет</h3>
      <div className={styles.colors}>
        {colors.map(([key, value]) => {
          return (
            <div key={key} className="flex align-items-center">
              <RadioButton
                inputId={key}
                name="color"
                value={value}
                onChange={(e: RadioButtonChangeEvent) => {
                  setColor(e.value);
                  setColorName(key as keyof typeof CardColors);
                }}
                checked={color === value}
              />
              <label htmlFor={key} className="ml-2">
                <div
                  className={styles["color-box"]}
                  style={{ backgroundColor: `${value}` }}
                ></div>
              </label>
            </div>
          );
        })}
      </div>

      <h3 className={styles.headers}>Выберите шаблон</h3>
      {templates.map(([key, value]) => {
        return (
          <div key={key} className="flex align-items-center">
            <RadioButton
              inputId={key}
              name="template"
              value={value}
              onChange={(e: RadioButtonChangeEvent) => {
                setTemplate(e.value);
                setTemplateName(key as keyof typeof CardTemplates);
              }}
              checked={template === value}
            />
            <label htmlFor={key} className="ml-2">
              <div
                className={styles.card}
                style={{ backgroundColor: `${color}` }}
                key={key}
              >
                <img
                  src={logo}
                  alt="логотип Т1"
                  className={`${
                    value === CardTemplates.FULL
                      ? styles["logo-first-case"]
                      : value === CardTemplates.MIDDLE
                      ? styles["logo-second-case"]
                      : styles["logo-third-case"]
                  } ${styles.logo}`}
                />
                <img
                  src={name}
                  alt="логотип Дебют"
                  className={styles[`name`]}
                />
                <p className={styles.initials}>Иван Иванов</p>
                {value === CardTemplates.FULL && (
                  <p className={styles.number}>0000 0000 0000 0000</p>
                )}
                {(value === CardTemplates.FULL ||
                  value === CardTemplates.MIDDLE) && (
                  <p className={styles.date}>12/25</p>
                )}
              </div>
            </label>
          </div>
        );
      })}

      <Button type="submit" label="Создать" />
    </form>
  );
}

export default FormCreateCard;
