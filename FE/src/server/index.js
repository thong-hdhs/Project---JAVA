import { createServer, Model, Response } from "miragejs";

import authServerConfig from "./auth-server";
import ShopServerConfig from "./shop-server";
import { products } from "@/constant/data";
import { faker } from "@faker-js/faker";
import { calendarEvents } from "./app/data";
import calendarServerConfig from "./app/calendar/calendar-server";

const previousDay = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
const dayBeforePreviousDay = new Date(
  new Date().getTime() - 24 * 60 * 60 * 1000 * 2
);

createServer({
  models: {
    user: Model,
    product: Model,
    calendarEvent: Model,
  },

  seeds(server) {
    server.create("user", {
      email: "dashcode@gmail.com",
      password: "dashcode",
    });

    products.forEach((product, i) => {
      server.create("product", {
        id: i + 1,
        img: product.img,
        category: product.category,
        name: product.name,
        subtitle: product.subtitle,
        desc: product.desc,
        rating: product.rating,
        price: product.price,
        oldPrice: product.oldPrice,
        percent: product.percent,
        brand: product.brand,
      });
    });

    calendarEvents.forEach((element) => {
      server.create("calendarEvent", {
        id: faker.string.uuid(),
        title: element.title,
        start: element.start,
        end: element.end,
        allDay: element.allDay,
        extendedProps: {
          calendar: element.extendedProps.calendar,
        },
      });
    });
  },

  routes() {
    this.namespace = "api";

    this.post("/auth/login", (_, request) => {
      const { email, password } = JSON.parse(request.requestBody);

      const users = [
        {
          id: "1",
          email: "admin@labodc.com",
          role: "SYSTEM_ADMIN",
          name: "System Admin",
        },
        {
          id: "2",
          email: "lab@labodc.com",
          role: "LAB_ADMIN",
          name: "Lab Admin",
        },
        {
          id: "3",
          email: "company@techcorp.com",
          role: "COMPANY",
          name: "Tech Corp",
        },
        {
          id: "4",
          email: "mentor@expert.com",
          role: "MENTOR",
          name: "Mentor User",
        },
        {
          id: "5",
          email: "talent1@example.com",
          role: "TALENT",
          name: "Talent User",
        },
      ];

      const user = users.find((u) => u.email === email);

      if (!user || password !== "password") {
        return new Response(
          401,
          {},
          { message: "Invalid email or password" }
        );
      }

      return {
        user,
        token: `fake-token-${user.role}`,
      };
    });

    authServerConfig(this);
    ShopServerConfig(this);
    calendarServerConfig(this);

    this.timing = 500;
  },
});