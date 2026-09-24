FROM node:20
WORKDIR /app
COPY MotorizApp/package*.json ./
RUN npm install --legacy-peer-deps
COPY MotorizApp/ .
CMD ["npm", "start", "--", "--reset-cache"]