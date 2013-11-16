load E2M12J;
load E3M12J;
load E4M12J;
load E5M12J;
load E6M12J;
load E7M12J;

res(1,:) = E2M12J(:,2)./E2M12J(:,1);
res(2,:) = E3M12J(:,2)./E3M12J(:,1);
res(3,:) = E4M12J(:,2)./E4M12J(:,1);
res(4,:) = E5M12J(:,2)./E5M12J(:,1);
res(5,:) = E6M12J(:,2)./E6M12J(:,1);
res(6,:) = E7M12J(:,2)./E7M12J(:,1);

for i = 1:6
    m(i) = mean(res(i,:));
    s(i) = std(res(i,:));
endfor

x = 2:1:7;

errorbar(x, m, s)
axis([1 8 1 2])
xlabel ("Total de máquinas");
ylabel ("makespan(online) / makespan(óptimo)");
print -dsvg fig2.svg
