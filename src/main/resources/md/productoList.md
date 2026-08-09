# Guía Técnica — Listado de Productos en Angular

## Análisis y explicación de `productos-list.ts`

**Módulo:** Productos
**Componente:** `ProductosListComponent`
**Tecnología:** Angular + Angular Material + RxJS
**Funcionalidades:** Tabla, búsqueda, debounce, filtros, contadores y paginación.

---

## 1. Objetivo del componente

El componente `ProductosListComponent` tiene como responsabilidad mostrar y administrar el listado de productos.

Actualmente concentra las siguientes funcionalidades:

* Cargar productos desde el backend.
* Mostrar productos en una tabla de Angular Material.
* Buscar productos.
* Aplicar debounce de 300 ms al buscador.
* Filtrar por estado.
* Filtrar por categoría.
* Mostrar contadores.
* Mostrar estado de carga.
* Mostrar estados vacíos.
* Paginar los resultados.
* Navegar hacia creación y edición de productos.

### Arquitectura general

```text
                    BACKEND / API
                         │
                         ▼
                 ProductoService
                         │
                         ▼
                  productos[]
                         │
                         ▼
                 aplicarFiltros()
                         │
          ┌──────────────┼──────────────┐
          │              │              │
          ▼              ▼              ▼
       Búsqueda        Estado       Categoría
       debounce
          │              │              │
          └──────────────┼──────────────┘
                         │
                         ▼
                productosFiltrados
                         │
                         ▼
                 dataSource.data
                         │
                 ┌───────┴───────┐
                 ▼               ▼
               Tabla         Paginador
```

---

# 2. Imports de Angular

```ts
import {
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
  inject
} from '@angular/core';
```

Estos imports proporcionan las funcionalidades principales del componente.

### `Component`

Permite definir la clase como un componente Angular.

### `OnInit`

Permite utilizar:

```ts
ngOnInit()
```

Este método se ejecuta cuando Angular inicializa el componente.

### `OnDestroy`

Permite utilizar:

```ts
ngOnDestroy()
```

Se utiliza principalmente para limpiar suscripciones y evitar fugas de memoria.

### `ViewChild`

Permite obtener una referencia a componentes presentes en el HTML.

En nuestro caso:

```ts
@ViewChild(MatPaginator)
```

permite acceder al paginador desde TypeScript.

### `inject`

Permite obtener servicios directamente:

```ts
private productoService = inject(ProductoService);
private router = inject(Router);
```

---

# 3. CommonModule y Router

```ts
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
```

## CommonModule

Permite utilizar funcionalidades comunes de Angular, como:

```html
*ngIf
*ngFor
```

También proporciona pipes comunes, por ejemplo:

```html
{{ precio | number }}
```

Como el componente es `standalone`, se agrega:

```ts
imports: [
  CommonModule
]
```

## Router

Se utiliza para navegar entre las diferentes pantallas.

Para crear un producto:

```ts
this.router.navigate([
  '/productos/nuevo'
]);
```

Para editar:

```ts
this.router.navigate([
  '/productos',
  producto.id
]);
```

---

# 4. Angular Material Paginator

```ts
import { MatPaginator } from '@angular/material/paginator';
import { MatPaginatorModule } from '@angular/material/paginator';
```

Aquí existen dos elementos diferentes.

## `MatPaginatorModule`

Permite utilizar el componente:

```html
<mat-paginator>
</mat-paginator>
```

Por eso se agrega:

```ts
imports: [
  CommonModule,
  MaterialModule,
  MatPaginatorModule
]
```

## `MatPaginator`

Es la clase que permite controlar el paginador desde TypeScript.

Por ejemplo:

```ts
this.paginator.firstPage();
```

Esto lleva al usuario nuevamente a la primera página.

---

# 5. MatTableDataSource

```ts
import { MatTableDataSource } from '@angular/material/table';
```

`MatTableDataSource` es la fuente de datos utilizada por la tabla.

En el HTML tenemos:

```html
<table
  mat-table
  [dataSource]="dataSource"
>
```

Por lo tanto:

```ts
dataSource
```

es la información que consume la tabla.

Además, `MatTableDataSource` tiene integración con:

* Tabla.
* Paginador.
* Ordenamiento.
* Filtrado.

En nuestro componente:

```ts
dataSource =
  new MatTableDataSource<Producto>([]);
```

Inicialmente la tabla comienza vacía.

---

# 6. RxJS y el buscador

```ts
import {
  Subject,
  debounceTime,
  distinctUntilChanged,
  takeUntil
} from 'rxjs';
```

Estas herramientas se utilizan principalmente para controlar el buscador.

La arquitectura del buscador es:

```text
Usuario escribe
      │
      ▼
buscarProducto()
      │
      ▼
searchSubject.next()
      │
      ▼
debounceTime(300)
      │
      ▼
distinctUntilChanged()
      │
      ▼
aplicarFiltros()
```

---

# 7. Subject del buscador

```ts
private searchSubject =
  new Subject<string>();
```

El `Subject` funciona como un canal de comunicación.

Cuando el usuario escribe:

```text
laptop
```

se ejecuta:

```ts
this.searchSubject.next(
  input.value
);
```

El valor pasa entonces por el flujo RxJS.

---

# 8. Debounce de 300 ms

Una de las funcionalidades importantes es:

```ts
debounceTime(300)
```

Esto significa:

> Esperar 300 milisegundos después de la última tecla antes de ejecutar el filtro.

Por ejemplo, el usuario escribe:

```text
l
la
lap
lapt
lapto
laptop
```

Sin debounce, podríamos ejecutar seis búsquedas.

Con debounce:

```text
laptop
   │
   ▼
esperar 300 ms
   │
   ▼
ejecutar búsqueda
```

Esto reduce procesamiento innecesario y mejora la experiencia del usuario.

---

# 9. `distinctUntilChanged()`

```ts
distinctUntilChanged()
```

Evita procesar nuevamente el mismo valor.

Por ejemplo:

```text
laptop
laptop
laptop
```

Solo procesa el primer valor.

---

# 10. `takeUntil(destroy$)`

```ts
takeUntil(this.destroy$)
```

Permite cancelar automáticamente la suscripción cuando el componente es destruido.

Esto es importante para evitar fugas de memoria.

Cuando el componente se destruye:

```ts
ngOnDestroy(): void {

  this.destroy$.next();
  this.destroy$.complete();

  this.searchSubject.complete();

}
```

---

# 11. ProductoService

```ts
import { ProductoService } from '../producto.service';
```

El componente no debería realizar directamente las peticiones HTTP.

La responsabilidad se separa de esta manera:

```text
ProductosListComponent
        │
        ▼
ProductoService
        │
        ▼
API
        │
        ▼
Base de datos
```

Para obtener los productos utilizamos:

```ts
this.productoService.getProductos()
```

---

# 12. Modelo Producto

```ts
import { Producto } from
  '../../../core/models/producto.model';
```

El modelo permite mantener tipado el código.

Por ejemplo:

```ts
productos: Producto[] = [];
```

Esto indica que `productos` es un arreglo de objetos `Producto`.

También:

```ts
new MatTableDataSource<Producto>([]);
```

indica que el DataSource trabaja con productos.

---

# 13. Configuración del componente

```ts
@Component({
  selector: 'app-productos-list',
  standalone: true,
  imports: [
    CommonModule,
    MaterialModule,
    MatPaginatorModule
  ],
  templateUrl: './productos-list.html',
  styleUrls: ['./productos-list.scss']
})
```

## `selector`

```ts
selector: 'app-productos-list'
```

Define el nombre del componente.

## `standalone`

```ts
standalone: true
```

Significa que el componente no necesita ser declarado dentro de un `NgModule`.

## `imports`

Aquí declaramos las dependencias que utiliza el HTML.

## `templateUrl`

Indica el archivo HTML.

## `styleUrls`

Indica los estilos SCSS del componente.

---

# 14. Dependencias

```ts
private productoService =
  inject(ProductoService);

private router =
  inject(Router);
```

Tenemos dos dependencias principales.

### ProductoService

Obtiene información del backend.

### Router

Permite navegar hacia otras pantallas.

---

# 15. `searchSubject`

```ts
private searchSubject =
  new Subject<string>();
```

Es el canal que recibe el texto del buscador.

El HTML puede ejecutar:

```ts
buscarProducto(event)
```

y posteriormente:

```ts
this.searchSubject.next(
  input.value
);
```

---

# 16. `destroy$`

```ts
private destroy$ =
  new Subject<void>();
```

Controla la finalización de las suscripciones.

Cuando el componente desaparece:

```ts
this.destroy$.next();
this.destroy$.complete();
```

se detienen las suscripciones asociadas.

---

# 17. Paginación

Esta es una de las partes más importantes del componente.

El paginator se almacena internamente:

```ts
private _paginator?: MatPaginator;
```

Luego se obtiene mediante `ViewChild`:

```ts
@ViewChild(MatPaginator)
set paginator(
  paginator: MatPaginator | undefined
) {

  if (!paginator) {
    return;
  }

  this._paginator = paginator;

  this.dataSource.paginator =
    paginator;
}
```

---

# 18. ¿Por qué usamos un Setter para `ViewChild`?

El paginator está dentro de una condición:

```html
<mat-card *ngIf="!loading">
```

Mientras:

```ts
loading = true;
```

el paginator no existe en el DOM.

Por eso no es suficiente depender únicamente de:

```ts
ngAfterViewInit()
```

Cuando termina la carga:

```ts
loading = false;
```

Angular crea el paginator.

En ese momento se ejecuta el setter:

```ts
set paginator(...)
```

y se realiza:

```ts
this.dataSource.paginator =
  paginator;
```

De esta manera la conexión queda garantizada.

---

# 19. Flujo de creación del paginator

```text
loading = true
       │
       ▼
Paginator no existe
       │
       ▼
Se solicita información
       │
       ▼
API responde
       │
       ▼
loading = false
       │
       ▼
Angular crea paginator
       │
       ▼
@ViewChild setter
       │
       ▼
dataSource.paginator
       │
       ▼
Paginación funcionando
```

---

# 20. Columnas de la tabla

```ts
displayedColumns: string[] = [
  'codigo',
  'nombre',
  'categoria',
  'marca',
  'precioVenta',
  'activo',
  'afectaIgv',
  'acciones'
];
```

Este arreglo controla las columnas visibles.

Debe coincidir con los `matColumnDef` del HTML.

Por ejemplo:

```html
<ng-container matColumnDef="codigo">
```

corresponde a:

```ts
'codigo'
```

El orden del arreglo determina el orden visual de las columnas.

---

# 21. DataSource

```ts
dataSource =
  new MatTableDataSource<Producto>([]);
```

El `dataSource` es el vínculo entre los datos y la tabla.

La tabla consume:

```html
[dataSource]="dataSource"
```

Y el paginator se conecta mediante:

```ts
this.dataSource.paginator =
  paginator;
```

La relación es:

```text
productos
    │
    ▼
dataSource
    │
    ├──────────► Tabla
    │
    └──────────► Paginator
```

---

# 22. Lista original de productos

```ts
productos: Producto[] = [];
```

Esta lista representa todos los productos recibidos desde el backend.

Es importante que esta lista permanezca intacta.

Por ejemplo:

```text
productos
├── Laptop
├── Mouse
├── Teclado
├── Monitor
└── Impresora
```

Cuando aplicamos filtros no modificamos esta lista.

---

# 23. Estado del buscador

```ts
searchValue = '';
```

Contiene el texto introducido por el usuario.

Ejemplo:

```text
searchValue = "laptop"
```

---

# 24. Filtro de estado

```ts
estadoFiltro:
  | 'todos'
  | 'activos'
  | 'inactivos' = 'todos';
```

Solo permite tres estados:

```text
todos
activos
inactivos
```

Esto proporciona seguridad de tipos.

---

# 25. Filtro de categoría

```ts
categoriaFiltro = '';
```

Si está vacío:

```text
Todas las categorías
```

Si contiene:

```text
Laptops
```

solo se muestran productos de esa categoría.

---

# 26. Categorías disponibles

```ts
categorias: string[] = [];
```

Contiene las categorías que se mostrarán en el selector.

Ejemplo:

```text
[
  "Accesorios",
  "Laptops",
  "Monitores",
  "Periféricos"
]
```

---

# 27. Estado de carga

```ts
loading = true;
```

Controla la pantalla de carga.

Mientras sea `true`:

```html
<mat-spinner>
```

Cuando sea `false`:

```html
<mat-card>
```

---

# 28. `ngOnInit`

```ts
ngOnInit(): void {

  this.configurarBuscador();

  this.cargarProductos();

}
```

Cuando inicia el componente:

1. Se configura el buscador.
2. Se cargan los productos.

---

# 29. `cargarProductos()`

```ts
cargarProductos(): void {

  this.loading = true;

  this.productoService.getProductos()
    ...
}
```

Primero mostramos el estado de carga.

Después llamamos al servicio.

---

# 30. Respuesta del backend

Cuando la API responde:

```ts
next: (data: Producto[]) => {
```

`data` contiene los productos.

Los almacenamos:

```ts
this.productos = data;
```

Esta será nuestra fuente original.

---

# 31. Obtener categorías

Después:

```ts
this.obtenerCategorias();
```

Se extraen las categorías de los productos.

Por ejemplo:

```text
Laptop → Tecnología
Mouse → Accesorios
Teclado → Accesorios
Monitor → Tecnología
```

El resultado será:

```text
Accesorios
Tecnología
```

sin duplicados.

---

# 32. Aplicar filtros iniciales

```ts
this.aplicarFiltros();
```

Al cargar por primera vez no existen filtros.

Por lo tanto, todos los productos pasan al `dataSource`.

---

# 33. Finalizar loading

```ts
this.loading = false;
```

Esto provoca que Angular muestre la tabla y el paginator.

En ese momento el setter de `ViewChild` conecta automáticamente el paginator.

---

# 34. Configuración del buscador

```ts
private configurarBuscador(): void {

  this.searchSubject
    .pipe(
      debounceTime(300),
      distinctUntilChanged(),
      takeUntil(this.destroy$)
    )
    .subscribe((search: string) => {

      this.searchValue = search;

      this.aplicarFiltros();

    });

}
```

El flujo es:

```text
Usuario escribe
      │
      ▼
searchSubject
      │
      ▼
300 ms
      │
      ▼
¿Cambió el valor?
      │
      ▼
aplicarFiltros()
```

---

# 35. `buscarProducto`

```ts
buscarProducto(event: Event): void {

  const input =
    event.target as HTMLInputElement;

  this.searchSubject.next(
    input.value
  );

}
```

Obtiene el valor del input y lo envía al Subject.

No llama directamente a `aplicarFiltros()` porque queremos mantener el debounce.

---

# 36. Cambio de estado

```ts
cambiarEstadoFiltro(
  filtro:
    | 'todos'
    | 'activos'
    | 'inactivos'
): void {

  this.estadoFiltro = filtro;

  this.aplicarFiltros();

}
```

Los filtros de selección no necesitan debounce.

El usuario selecciona una opción y el resultado se actualiza inmediatamente.

---

# 37. Cambio de categoría

```ts
cambiarCategoriaFiltro(
  categoria: string
): void {

  this.categoriaFiltro = categoria;

  this.aplicarFiltros();

}
```

Igualmente, el resultado se actualiza inmediatamente.

---

# 38. Obtener categorías

```ts
private obtenerCategorias(): void {

  const categorias =
    this.productos
      .map(
        producto =>
          producto.categoria?.nombre
      )
      .filter(
        (nombre): nombre is string =>
          !!nombre
      );

  this.categorias = [
    ...new Set(categorias)
  ].sort();

}
```

El proceso es:

```text
productos
    │
    ▼
map()
    │
    ▼
obtener nombres
    │
    ▼
filter()
    │
    ▼
eliminar valores vacíos
    │
    ▼
Set
    │
    ▼
eliminar duplicados
    │
    ▼
sort()
    │
    ▼
categorias
```

---

# 39. `aplicarFiltros()`

Este método concentra la lógica de filtrado.

Combina:

```text
Búsqueda
+
Estado
+
Categoría
```

Siempre parte de:

```ts
this.productos
```

y no de:

```ts
this.dataSource.data
```

Esto es importante porque permite volver a aplicar filtros desde la lista original.

---

# 40. Normalización de búsqueda

```ts
const search =
  this.searchValue
    .trim()
    .toLowerCase();
```

Esto permite que:

```text
"Laptop"
" laptop "
"LAPTOP"
```

se comporten de la misma manera.

Todos terminan siendo:

```text
"laptop"
```

---

# 41. Búsqueda

Se busca en:

* Código.
* Nombre.
* Categoría.
* Marca.

Por ejemplo:

```ts
producto.codigo
producto.nombre
producto.categoria?.nombre
producto.marca?.nombre
```

Así el usuario puede escribir:

```text
P001
Laptop
Lenovo
Accesorios
```

y obtener resultados relacionados.

---

# 42. Filtro de estado

Para activos:

```ts
if (
  this.estadoFiltro === 'activos' &&
  !producto.activo
) {
  return false;
}
```

Para inactivos:

```ts
if (
  this.estadoFiltro === 'inactivos' &&
  producto.activo
) {
  return false;
}
```

Si el producto no cumple el criterio:

```ts
return false;
```

queda fuera del resultado.

---

# 43. Filtro de categoría

```ts
if (
  this.categoriaFiltro &&
  producto.categoria?.nombre !==
    this.categoriaFiltro
) {
  return false;
}
```

Si seleccionamos:

```text
Laptops
```

solo pasan productos cuya categoría sea:

```text
Laptops
```

---

# 44. Actualización del DataSource

Una vez calculados los productos filtrados:

```ts
this.dataSource.data =
  productosFiltrados;
```

Este es un punto importante.

No creamos nuevamente:

```ts
new MatTableDataSource(...)
```

cada vez que cambia el filtro.

Mantenemos el mismo objeto `dataSource`.

Esto mantiene estable la conexión con el paginator.

---

# 45. Reinicio de paginación

Después de cambiar los filtros:

```ts
if (this._paginator) {

  this._paginator.firstPage();

}
```

Esto evita situaciones como:

```text
Página actual: 5
Resultados después del filtro: 2 productos
```

El sistema vuelve automáticamente a:

```text
Página 1
```

---

# 46. Contador total

```ts
get totalProductos(): number {

  return this.productos.length;

}
```

Representa el total recibido desde el backend.

Ejemplo:

```text
Total productos: 128
```

---

# 47. Contador de activos

```ts
get productosActivos(): number {

  return this.productos.filter(
    producto => producto.activo
  ).length;

}
```

Cuenta únicamente los productos activos.

---

# 48. Contador de inactivos

```ts
get productosInactivos(): number {

  return this.productos.filter(
    producto => !producto.activo
  ).length;

}
```

Cuenta los productos inactivos.

---

# 49. Productos filtrados

```ts
get productosFiltrados(): number {

  return this.dataSource.data.length;

}
```

Este contador representa los productos que quedan después de aplicar filtros.

Ejemplo:

```text
Total:               128
Productos filtrados:   7
```

---

# 50. `hayFiltrosActivos`

```ts
get hayFiltrosActivos(): boolean {

  return !!(
    this.searchValue ||
    this.estadoFiltro !== 'todos' ||
    this.categoriaFiltro
  );

}
```

Determina si el usuario tiene algún filtro activo.

Esto permite diferenciar dos situaciones:

### Sin productos registrados

```text
No hay productos registrados
```

### Sin resultados

```text
No encontramos productos
```

La segunda situación ocurre cuando existen productos, pero los filtros no encuentran coincidencias.

---

# 51. Limpiar filtros

```ts
limpiarFiltros(): void {

  this.searchValue = '';

  this.estadoFiltro = 'todos';

  this.categoriaFiltro = '';

  this.aplicarFiltros();

  if (this._paginator) {

    this._paginator.firstPage();

  }

}
```

Restablece:

```text
Búsqueda  → vacía
Estado    → todos
Categoría → todas
Página    → 1
```

El usuario vuelve a visualizar el catálogo completo.

---

# 52. Crear producto

```ts
nuevoProducto(): void {

  this.router.navigate([
    '/productos/nuevo'
  ]);

}
```

Navega al formulario de creación.

---

# 53. Editar producto

```ts
editarProducto(
  producto: Producto
): void {

  this.router.navigate([
    '/productos',
    producto.id
  ]);

}
```

Navega al formulario correspondiente al producto seleccionado.

---

# 54. Ciclo completo del componente

El comportamiento completo puede resumirse así:

```text
┌──────────────────────────────┐
│       ProductosListComponent │
└──────────────┬───────────────┘
               │
               ▼
       cargarProductos()
               │
               ▼
       ProductoService
               │
               ▼
             API
               │
               ▼
        productos[]
               │
       ┌───────┴────────┐
       │                │
       ▼                ▼
 categorías        filtros
                        │
           ┌────────────┼────────────┐
           │            │            │
           ▼            ▼            ▼
       búsqueda       estado      categoría
       debounce
           │            │            │
           └────────────┼────────────┘
                        │
                        ▼
               productosFiltrados
                        │
                        ▼
                dataSource.data
                        │
              ┌─────────┴─────────┐
              │                   │
              ▼                   ▼
            Tabla             Paginator
```

---

# 55. Responsabilidades principales

| Responsabilidad      | Método / Propiedad                     |
| -------------------- | -------------------------------------- |
| Cargar productos     | `cargarProductos()`                    |
| Buscar               | `buscarProducto()`                     |
| Debounce             | `debounceTime(300)`                    |
| Filtrar estado       | `cambiarEstadoFiltro()`                |
| Filtrar categoría    | `cambiarCategoriaFiltro()`             |
| Gestionar paginación | `MatPaginator`                         |
| Gestionar tabla      | `MatTableDataSource`                   |
| Navegar              | `nuevoProducto()` / `editarProducto()` |

---

# 56. Concepto clave: lista original vs lista filtrada

Una decisión importante de arquitectura es mantener dos conceptos separados.

### Lista original

```ts
productos: Producto[] = [];
```

Representa:

> Todo lo que vino del backend.

### Datos mostrados

```ts
dataSource.data
```

Representa:

> Lo que debe mostrarse después de aplicar filtros.

Por ejemplo:

```text
Backend
  │
  ▼
128 productos
  │
  ▼
productos[]
  │
  │ filtro "Laptop"
  ▼
7 productos
  │
  ▼
dataSource.data
```

Esto permite cambiar los filtros sin perder la información original.

---

# 57. Concepto clave: DataSource único

La implementación utiliza:

```ts
dataSource =
  new MatTableDataSource<Producto>([]);
```

una sola instancia.

Posteriormente solamente cambia:

```ts
this.dataSource.data =
  productosFiltrados;
```

Esto es preferible a crear constantemente:

```ts
new MatTableDataSource(...)
```

porque el paginator permanece correctamente conectado.

La relación estable es:

```text
                  dataSource
                 /          \
                /            \
               ▼              ▼
            Tabla          Paginator
```

---

# 58. Concepto clave: paginación y filtros

Cuando se aplica un filtro:

```text
Usuario filtra
     │
     ▼
Se calculan resultados
     │
     ▼
dataSource.data cambia
     │
     ▼
Paginator recalcula páginas
     │
     ▼
firstPage()
     │
     ▼
Usuario queda en página 1
```

Esto genera una experiencia más predecible.

---

# 59. Buenas prácticas aplicadas

La implementación incorpora varias buenas prácticas.

### Tipado

```ts
Producto[]
```

en lugar de utilizar objetos sin tipo.

### Debounce

```ts
debounceTime(300)
```

para evitar búsquedas excesivas.

### Limpieza de suscripciones

```ts
takeUntil(this.destroy$)
```

para evitar fugas de memoria.

### Separación de responsabilidades

El acceso al backend se realiza mediante:

```ts
ProductoService
```

### DataSource estable

Se mantiene una única instancia de:

```ts
MatTableDataSource
```

### Paginación segura

El paginator se obtiene mediante un setter de `ViewChild`, debido al:

```html
*ngIf="!loading"
```

### Reinicio de página

Los filtros llevan al usuario a la primera página.

---

# 60. Resumen final

El componente `ProductosListComponent` funciona como el controlador de la vista de productos.

Su flujo principal es:

```text
1. Inicializar componente
          ↓
2. Configurar buscador
          ↓
3. Cargar productos
          ↓
4. Guardar lista original
          ↓
5. Obtener categorías
          ↓
6. Aplicar filtros iniciales
          ↓
7. Mostrar tabla
          ↓
8. Conectar paginator
          ↓
9. Usuario busca / filtra
          ↓
10. Aplicar filtros
          ↓
11. Actualizar dataSource
          ↓
12. Regresar a página 1
```

La idea arquitectónica más importante es mantener separadas estas tres capas:

```text
productos[]
     │
     │ información original
     ▼
aplicarFiltros()
     │
     │ información procesada
     ▼
dataSource.data
     │
     ├──────────────► Tabla
     │
     └──────────────► Paginador
```

De esta manera, el componente puede seguir creciendo sin perder el control sobre el origen de los datos, los filtros y la presentación.

---

# 61. Próximas mejoras recomendadas

La implementación actual constituye una buena base para un listado profesional.

Como siguientes pasos se pueden incorporar:

1. **Ordenamiento por columnas**

    * Código.
    * Producto.
    * Precio.
    * Estado.

2. **Persistencia de filtros**

    * Mantener filtros al volver desde editar.

3. **Filtros avanzados**

    * Rango de precios.
    * Marca.
    * IGV.
    * Estado.

4. **Acciones de producto**

    * Editar.
    * Activar/desactivar.
    * Ver detalle.

5. **Mejoras UX**

    * Skeleton loading.
    * Empty states diferenciados.
    * Indicador de filtros activos.
    * Contador de resultados.
    * Mensajes de búsqueda sin resultados.

6. **Optimización para grandes catálogos**

    * Paginación desde backend.
    * Búsqueda server-side.
    * Filtros enviados mediante parámetros HTTP.

7. **Refactorización**

    * Separar la lógica de filtros si el componente continúa creciendo.
    * Crear modelos específicos para filtros.
    * Centralizar la lógica de consulta.

---

# Conclusión

La implementación actual establece una base sólida para un listado de productos moderno en Angular.

La combinación de:

```text
Angular Material
+
MatTableDataSource
+
MatPaginator
+
RxJS
+
debounceTime(300)
+
filtros
+
ProductoService
```

permite construir una pantalla de catálogo eficiente, mantenible y preparada para incorporar nuevas funcionalidades.

El punto fundamental es que el componente conserva la información original en:

```ts
productos[]
```

procesa esa información mediante:

```ts
aplicarFiltros()
```

y finalmente presenta los resultados mediante:

```ts
dataSource.data
```

mientras `MatPaginator` controla la cantidad de registros visibles por página.

Este patrón permite mantener una separación clara entre **datos originales, lógica de filtrado y presentación de información**.
