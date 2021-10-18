public class EqualsWithotNullArgumentCheck {
    public boolean equals(Object o) {
        return (o instanceof Person) && 
               this.getName().equals(((Person)o).getName());
    }
    
}
