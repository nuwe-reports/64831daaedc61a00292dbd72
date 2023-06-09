# Proyecto de Citas

Este proyecto implementa una API para gestionar citas médicas, que incluye controladores para las entidades Appointment, Doctor, Patient y Room.

## Tecnologías utilizadas

- Java
- Spring Boot 2.7
- MySQL
- JUnit
- Docker
- Kubernetes

## Estructura del proyecto

El proyecto sigue una estructura básica de aplicación Spring Boot, con los siguientes paquetes principales:

- `com.example.demo.controllers.appointment`: Contiene la implementación de los controladores y la lógica relacionada con las citas médicas.
- `com.example.demo.controllers.doctor`: Contiene la implementación de los controladores y la lógica relacionada con los doctores.
- `com.example.demo.controllers.patient`: Contiene la implementación de los controladores y la lógica relacionada con los pacientes.
- `com.example.demo.controllers.room`: Contiene la implementación de los controladores y la lógica relacionada con las salas.

## Controladores

### AppointmentController

El controlador AppointmentController maneja las operaciones relacionadas con las citas médicas.

- Obtener todas las citas:
    - Método: GET 
    - Ruta: `/api/appointments`

- Obtener una cita por su ID:
    - Método: GET
    - Ruta: `/api/appointments/{id}`

- Crear una nueva cita:
    - Método: POST
    - Ruta: `/api/appointment`

- Eliminar una cita por su ID:
    - Método: DELETE
    - Ruta: `/api/appointments/{id}`

- Eliminar todas las citas:
    - Método: DELETE
    - Ruta: `/api/appointments`

### DoctorController

El controlador DoctorController maneja las operaciones relacionadas con los doctores.

- Obtener todos los doctores:
    - Método: GET
    - Ruta: `/api/doctors`

- Obtener un doctor por su ID:
    - Método: GET
    - Ruta: `/api/doctors/{id}`

- Crear un nuevo doctor:
    - Método: POST
    - Ruta: `/api/doctor`

- Eliminar un doctor por su ID:
    - Método: DELETE
    - Ruta: `/api/doctors/{id}`

- Eliminar todos los doctores:
    - Método: DELETE
    - Ruta: `/api/doctors`

### PatientController

El controlador PatientController maneja las operaciones relacionadas con los pacientes.

- Obtener todos los pacientes:
    - Método: GET
    - Ruta: `/api/patients`

- Obtener un paciente por su ID:
    - Método: GET
    - Ruta: `/api/patients/{id}`

- Crear un nuevo paciente:
    - Método: POST
    - Ruta: `/api/patient`

- Eliminar un paciente por su ID:
    - Método: DELETE
    - Ruta: `/api/patients/{id}`

- Eliminar todos los pacientes:
    - Método: DELETE
    - Ruta: `/api/patients`

## Diagrama de clases UML
![UML-diagram-class.png](..%2F..%2F..%2F..%2F11_stepik%2FUML-diagram-class.png)