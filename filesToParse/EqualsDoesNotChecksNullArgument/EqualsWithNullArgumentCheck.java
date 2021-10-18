public class EqualsWithNullArgumentCheck {
    class Person {

        public boolean equals(Object o) {
            return (o != null) && 
                   (o instanceof Person) && 
                   this.getName().equals(((Person)o).getName());
        }
  
    }
}
