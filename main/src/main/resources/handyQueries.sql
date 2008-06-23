//Summary of last snapshot
select sec.code, sna.last_price, sna.difference, sna.return_on_capital, sna.trailing_stop_loss, sna.channel_taken
from snapshot sna, security sec, transaction tra, (select buy_transaction_id, max(snapshot_number) as snapshot_number from snapshot group by 1) snaview
where 
sna.buy_transaction_id = tra.id and
tra.security_id = sec.id and
sna.buy_transaction_id = snaview.buy_transaction_id and
sna.buy_transaction_id not in (select buy_transaction_fk from buyselltransaction) and
sna.snapshot_number = snaview.snapshot_number;

//Portfolio summary
select sum(sna.difference) as 'Portfolio Revenue', sum(market_value) as 'Market Value'
from snapshot sna, security sec, transaction tra, (select buy_transaction_id, max(snapshot_number) as snapshot_number from snapshot group by 1) snaview
where 
sna.buy_transaction_id = tra.id and
tra.security_id = sec.id and
sna.buy_transaction_id = snaview.buy_transaction_id and
sna.buy_transaction_id not in (select buy_transaction_fk from buyselltransaction) and 
sna.snapshot_number = snaview.snapshot_number;
