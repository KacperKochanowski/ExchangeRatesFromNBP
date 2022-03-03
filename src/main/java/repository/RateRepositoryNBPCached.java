package repository;

import nbpApi.Rate;
import nbpApi.RateTable;
import nbpApi.Table;
import nbpApi.URIGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RateRepositoryNBPCached implements RateRepository {
    private static final Rate RATE_PLN = Rate.builder().code("PLN").mid(1).currency("złoty").build();
    private ApiRepository<RateTable> rates = new ApiRepository<>(RateTable.class);
    private Map<Table, Map<LocalDate, RateTable>> cache = new HashMap<>();

    @Override
    public List<Rate> findByTableAndDate(Table table, LocalDate date) throws IOException, InterruptedException {
        if (cache.containsKey(table) && cache.get(table).containsKey(date)) {
            return cache.get(table).get(date).getRates();
        }
        final List<RateTable> list = this.rates.getList(URIGenerator.tableByDateJson(table, date));
        if (!list.isEmpty()) {
            cache.putIfAbsent(table, new HashMap<>());
            RateTable rateTable = list.get(0);
            final String strDate = rateTable.getEffectiveDate();
            final LocalDate dateTable = LocalDate.parse(strDate);
            rateTable.getRates().add(RATE_PLN);
            cache.get(table).putIfAbsent(dateTable, rateTable);
            return rateTable.getRates();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Rate> findByTableLast(Table table) throws IOException, InterruptedException {
        final List<Rate> rates = this.rates.getList(URIGenerator.currentTableJson(table)).get(0).getRates();
        rates.add(RATE_PLN);
        return  rates;
    }
}