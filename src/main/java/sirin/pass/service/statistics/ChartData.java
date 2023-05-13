package sirin.pass.service.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ChartData {

    private List<String> labels;
    private List<Long> attendedCounts;
    private List<Long> canceledCounts;

}
