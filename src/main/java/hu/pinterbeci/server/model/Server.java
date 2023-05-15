package hu.pinterbeci.server.model;

import hu.pinterbeci.server.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

//a szervereket reprezentáló model JPA entitás, adatbázis tábla egy rekordjának leképezése Java osztályra
@Entity

//lombok: bolierplate kódot megszüntető library, amely futásidőben biztosítja a kellő metódusokat/konstruktorokat, vagyis a kellő kódot
//lombok annotációk:
@Data //lombok annotáció :  @ToString, @EqualsAndHashCode, @Getter @Setter, and @RequiredArgsConstructor
@NoArgsConstructor // a lombok gondoskodik arról, hogy futásidőbe generáljon nekünk argumentum nélküli konstruktort
@AllArgsConstructor // minden argumentumú konstruktor
public class Server {

    //mivel egy JPA entitásról beszülünk, fontos számára az egyedi ID, amiért szólt is a Spring, így felannotáltuk
    // az id field-ünket, hogy megfelelő módon legyen az generálva.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //egy constraintet(megszorítást) helyezük rá, hogy mindenegyes ip-cím egyedi legyen
    //azután pedig egy validációt (ellenőrzést), hogy nem lehet üres az értéke
    @Column(unique = true)
    @NotEmpty(message = "IP address cannot be empty or null!")
    private String ipAddress;
    private String name;
    private String memory;
    private String type;
    private String imageUrl;
    private Status status;
}
