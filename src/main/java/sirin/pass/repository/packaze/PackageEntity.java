package sirin.pass.repository.packaze;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sirin.pass.repository.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@ToString
@Getter
@Setter
@Table(name = "package")
public class PackageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packageSeq;

    private String packageName;

    private Integer count;

    private Integer period;

}


