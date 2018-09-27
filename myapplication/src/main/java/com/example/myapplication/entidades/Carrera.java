package com.example.myapplication.entidades;

    import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

    /**
     * Created by CHENAO on 6/08/2017.
     */

    public class Carrera {

        private String nombre;
        private String titulo;
        private String duracion;

    /*    private Bitmap imagen;
        private String rutaImagen;*/


      /*  public String getRutaImagen() {
            return rutaImagen;
        }

        public void setRutaImagen(String rutaImagen) {
            this.rutaImagen = rutaImagen;
        }*/

        public String getNombre() {
            return nombre;
        }

        public String getTitulo() {
            return titulo;
        }

        /*public void setDato(String dato) {
            this.dato = dato;

            try {
                byte[] byteCode= Base64.decode(dato,Base64.DEFAULT);
                //this.imagen= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);

                int alto=100;//alto en pixeles
                int ancho=150;//ancho en pixeles

                Bitmap foto=BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                this.imagen=Bitmap.createScaledBitmap(foto,alto,ancho,true);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public Bitmap getImagen() {
            return imagen;
        }
*/
     /*   public void setImagen(Bitmap imagen) {
            this.imagen = imagen;
        }
*/
        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDuracion() {
            return duracion;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }


        public void setDuracion(String duracion) {
            this.duracion = duracion;
        }
    }

